import org.apache.commons.lang3.StringUtils;
import utilities.FileReader;

import java.util.*;

public class Day19b {
  public static void solve() {
    System.out.println(new Day19b().part1());
  }

  public long part1() {
    List<String> rules = FileReader.readToList("Day19_rules_part2");
    List<String> messages = FileReader.readToList("Day19_messages");

    Map<Integer, String> ruleMap = loadMap(rules);

    String regex = createRegex(0, ruleMap, new HashMap<>());

    return messages.stream()
      .filter(line -> line.matches(regex))
      .count();
  }

  public static String createRegex(int rule, Map<Integer, String> ruleMap, Map<Integer, String> cache) {
    if (cache.containsKey(rule)) {
      return cache.get(rule);
    }

    if (rule == 8) {
      String regex = String.format("(%s+)", createRegex(42, ruleMap, cache));
      cache.put(8, regex);
      return regex;
    }

    if (rule == 11) {
      String case42 = createRegex(42, ruleMap, cache);
      String case31 = createRegex(31, ruleMap, cache);

      String regex = "((AB)|(AABB)|(AAABBB)|(AAAABBBB)|(AAAAABBBBB)|(AAAAAABBBBBB))";
      regex = regex.replace("A", case42).replace("B", case31);

      cache.put(11, regex);
      return regex;
    }

    String ruleString = ruleMap.get(rule);
    if (ruleString.contains("\"")) {
      return ruleString.replace("\"", "");
    }

    StringBuilder stringBuilder = new StringBuilder("(");
    String[] parts = ruleString.split(" ");
    for (String part : parts) {
      if (StringUtils.isNumeric(part)) {
        stringBuilder.append(createRegex(Integer.parseInt(part), ruleMap, cache));
      }
      else if (part.equals("|")) {
        stringBuilder.append('|');
      }
    }
    stringBuilder.append(")");

    cache.put(rule, stringBuilder.toString());
    return stringBuilder.toString();
  }

  private Map<Integer, String> loadMap(List<String> rules) {
    Map<Integer, String> ruleMap = new HashMap<>();

    for (String line : rules) {
      String[] parts = line.split(": ");
      ruleMap.put(Integer.parseInt(parts[0]), parts[1]);
    }

    return ruleMap;
  }
}
