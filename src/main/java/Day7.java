import org.apache.commons.lang3.tuple.ImmutablePair;
import utilities.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

  public static void solve() {
    System.out.println(new Day7().part2());
  }

  private int part1() {
    List<String> allBags = FileReader.readToList("Day7_input");

    Set<String> bags = allBags.stream()
      .filter(bag -> containBags(bag, Collections.singleton("shiny gold")))
      .map(this::getBagName)
      .collect(Collectors.toSet());

    int count = bags.size();

    while(true) {
      allBags.stream()
        .filter(line -> containBags(line, bags))
        .map(this::getBagName)
        .forEach(bags::add);

      if(count == bags.size()) {
        break;
      }
      count = bags.size();
    }

    return -1;
  }

  private String getBagName(String bag) {
    return bag.split("bags")[0];
  }

  private boolean containBags(String line, Set<String> bags) {
    return bags.stream().anyMatch(bag -> line.contains(bag) && !line.startsWith(bag));
  }

  private int part2() {
    Map<String, List<ImmutablePair<String, Integer>>> map = createMap();

    List<ImmutablePair<String, Integer>> list = map.get("shiny gold");

    return list.stream()
      .map(pair -> calculatePart2(pair, map) * pair.getRight() + pair.getRight())
      .reduce(0, Integer::sum);
  }

  private int calculatePart2(ImmutablePair<String, Integer> bag, Map<String, List<ImmutablePair<String, Integer>>> map) {
    if(map.get(bag.getLeft()).isEmpty()) {
      return 0;
    }

    List<ImmutablePair<String, Integer>> list = map.get(bag.getLeft());
    return list.stream()
      .map(pair -> calculatePart2(pair, map) * pair.getRight() + pair.getRight())
      .reduce(0, Integer::sum);
  }

  private Map<String, List<ImmutablePair<String, Integer>>> createMap() {
    List<String> allLines = FileReader.readToList("Day7_input");

    Map<String, List<ImmutablePair<String, Integer>>> map = new HashMap<>();

    for(String line : allLines) {
      if(line.isEmpty()) {
        break;
      }

      addToMap(line, map);
    }
    return map;
  }

  private void addToMap(String line, Map<String, List<ImmutablePair<String, Integer>>> map) {
    String[] thisLine = line.substring(0, line.length() - 1).split("bags|bag");
    String key = thisLine[0].trim();

    if (line.contains("no other bags")) {
      map.put(key, new ArrayList<>());
    } else {
      List<ImmutablePair<String, Integer>> values = new ArrayList<>();
      for (int i = 1; i < thisLine.length; i++) {
        String[] splited = thisLine[i].trim().split("\\s+");
        String bag = splited[splited.length - 2] + " " + splited[splited.length - 1];
        int number = Integer.parseInt(splited[splited.length - 3]);
        values.add(new ImmutablePair<>(bag, number));
      }

      map.put(key, values);
    }
  }

}
