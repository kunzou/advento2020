import org.apache.commons.lang3.StringUtils;
import utilities.FileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14 {
  public static void solve() {
    System.out.println(new Day14().part2());
  }

  private long part1() {

    List<String> input = FileReader.readToList("Day14_input");

    String[] memory = new String[999999];
    String mask = "";
    int location = 0;
    String value = "";
    Pattern memoryPattern = Pattern.compile("\\[([0-9]*?)]");

    for (String line: input) {
      if(line.startsWith("mask")) {
        mask = line.split("=")[1].trim();
      }
      else {
        Matcher match = memoryPattern.matcher(line);
        if(match.find()) {
          location = Integer.parseInt(match.group(1));
        }
        value = toBinary(Integer.parseInt(line.split("=")[1].trim()));
        memory[location] = applyMask(mask, value);
      }
    }

    return Stream.of(memory)
      .filter(StringUtils::isNotBlank)
      .map(binaryValue -> Long.parseLong(binaryValue, 2))
      .reduce(Long::sum)
      .get();
  }

  String toBinary(int value) {
    return String.format("%36s" ,Integer.toBinaryString(value)).replace(' ', '0');
  }

  String applyMask(String mask, String value) {
    StringBuilder result = new StringBuilder();
    for(int i = 0; i < mask.length(); i++) {
      if(mask.charAt(i) != 'X') {
        result.append(mask.charAt(i));
      }
      else {
        result.append(value.charAt(i));
      }
    }
    return result.toString();
  }

  private long part2() {
    List<String> input = FileReader.readToList("Day14_input");

    String mask = "";
    int location = 0;
    int value = 0;
    Pattern memoryPattern = Pattern.compile("\\[([0-9]*?)]");

    Map<Long, Long> memory = new HashMap<>();

    for (String line: input) {
      if(line.startsWith("mask")) {
        mask = line.split("=")[1].trim();
      }
      else {
        Matcher match = memoryPattern.matcher(line);
        if(match.find()) {
          location = Integer.parseInt(match.group(1));
        }
        value = Integer.parseInt(line.split("=")[1].trim());

        long effectivelyValue = value;
        String maskedAddress = getMaskedAddress(mask, toBinary(location));
        List<StringBuilder> stringBuilders = new ArrayList<>();
        decodeAddress(mask, maskedAddress, 0, stringBuilders, new StringBuilder());
        stringBuilders.stream()
          .map(StringBuilder::toString)
          .map(mem -> Long.parseLong(mem, 2))
          .forEach(mem -> memory.put(mem, effectivelyValue));
      }
    }

    return memory.values().stream().reduce(0L, Long::sum);

  }

  String getMaskedAddress(String mask, String address) {
    StringBuilder result = new StringBuilder();
    for(int i = 0; i < mask.length(); i++) {
      if(mask.charAt(i) == '0') {
        result.append(address.charAt(i));
      }
      else if(mask.charAt(i) == '1') {
        result.append('1');
      }
      else if(mask.charAt(i) == 'X') {
        result.append('X');
      }
    }
    return result.toString();
  }

  void decodeAddress(String mask, String address, int index, List<StringBuilder> results, StringBuilder currentBuilder) {
    if(index == mask.length()) {
      results.add(currentBuilder);
      return;
    }

    if(mask.charAt(index) != 'X') {
      currentBuilder.append(address.charAt(index));
      decodeAddress(mask, address, index+1, results, currentBuilder);
    }
    else {
      StringBuilder newBuilder = new StringBuilder(currentBuilder.toString());
      currentBuilder.append('1');
      newBuilder.append('0');
      decodeAddress(mask, address, index+1, results, currentBuilder);
      decodeAddress(mask, address, index+1, results, newBuilder);
    }
  }
}
