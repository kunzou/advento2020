import utilities.FileReader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 {
  public static void solve() {
    System.out.println(new Day19().part1());
  }

  private Integer NUMBER_A = -1;
  private Integer NUMBER_B = -1;

  public long part1() {
    List<String> rules = FileReader.readToList("Day19_rules");
    List<String> messages = FileReader.readToList("Day19_messages");

    Map<Integer, Set<List<Integer>>> map = loadMap(rules);

    List<Integer> template = map.remove(0).stream().findFirst().get();

    Set<List<Integer>> possiblePatterns = new HashSet<>();
    possiblePatterns.add(new ArrayList<>());

    formPattern(map, possiblePatterns, new ArrayList<>(), new LinkedList<>(template));

    Set<String> possibleMessages = possiblePatterns.stream()
      .map(this::toMessage)
      .collect(Collectors.toSet());

    return messages.stream()
      .filter(possibleMessages::contains)
      .count();
  }

  private String toMessage(List<Integer> numbers) {
    StringBuilder stringBuilder = new StringBuilder();
    for(Integer number : numbers) {
      if(number.equals(NUMBER_A)) {
        stringBuilder.append("a");
      }
      else {
        stringBuilder.append("b");
      }
    }
    return stringBuilder.toString();
  }

  private void formPattern(Map<Integer, Set<List<Integer>>> map, Set<List<Integer>> possiblePatterns, List<Integer> currentPattern, LinkedList<Integer> template) {
    if(template.isEmpty()) {
      possiblePatterns.add(currentPattern);
      return;
    }

    Integer number = template.pollFirst();

    if(number.equals(NUMBER_A) || number.equals(NUMBER_B)) {
      currentPattern.add(number);
      formPattern(map, possiblePatterns, currentPattern, template);
    }
    else {
      Set<List<Integer>> sets = map.get(number);

      for(List<Integer> numbers : sets) {
        List<Integer> newPattern = new ArrayList<>(currentPattern);
        LinkedList<Integer> newTemplate = new LinkedList<>(template);
        for(int i = 0; i < numbers.size(); i++) {
          newTemplate.add(i, numbers.get(i));
        }
        formPattern(map, possiblePatterns, newPattern, newTemplate);
      }
    }
  }

  private Map<Integer, Set<List<Integer>>> loadMap(List<String> input) {
    Map<Integer, Set<List<Integer>>> map = new HashMap<>();

    for(String line : input) {
      Integer key = Integer.parseInt(line.split(": ")[0]);

      String[] rules = line.split(": ")[1].split("\\|");

      Set<List<Integer>> ruleSets = new HashSet<>();

      if(rules[0].trim().equals("\"a\"")) {
        NUMBER_A = key;
        continue;
      }

      if(rules[0].trim().equals("\"b\"")) {
        NUMBER_B = key;
        continue;
      }

      for(String rule : rules) {
        ruleSets.add(Stream.of(rule.trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
      }
      map.put(key, ruleSets);
    }
    return map;
  }

}
