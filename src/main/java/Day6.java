import utilities.FileReader;

import java.util.*;

public class Day6 {
  public static void solve() {
    System.out.println(new Day6().part2());
  }

  private int part1() {
    return FileReader.readToListSeparatedByEmptyLine("Day6_input").stream()
      .map(this::countEachGroup)
      .reduce(0, Integer::sum);
  }

  private int countEachGroup(String group) {
    Set<Character> answers = new HashSet<>();
    for (Character c: group.toCharArray()) {
      if(c != ' ') {
        answers.add(c);
      }
    }
    return answers.size();
  }

  private int part2() {
    return FileReader.readToListSeparatedByEmptyLine("Day6_input").stream()
      .map(this::countEachGroup2)
      .reduce(0, Integer::sum);
  }

  private int countEachGroup2(String group) {
    String[] groupArray = group.split(" ");

    int groupSize = groupArray.length;

    Map<Character, Integer> count = new HashMap<>();

    for(String line : groupArray) {
      for(Character c : line.toCharArray()) {
        count.merge(c, 1, Integer::sum);
      }
    }

    return (int)count.entrySet()
      .stream()
      .filter(stringLongEntry -> stringLongEntry.getValue() == groupSize)
      .map(Map.Entry::getKey)
      .count();
  }
}
