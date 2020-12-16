import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.FileReader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {
  public static void solve() {
    System.out.println(new Day16().part2());
  }

  private int part1() {
    List<String> input = FileReader.readToList("Day16");

    List<Pair<Integer, Integer>> ranges = getValidRanges(input);

    List<Integer> invalidNumbers = new ArrayList<>();

    boolean skip = true;

    for(String line : input) {
      if(line.startsWith("nearby tickets")) {
        skip = false;
        continue;
      }

      if(skip) {
        continue;
      }

      List<Integer> numbers = Stream.of(line.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

      for(int number : numbers) {
        if(ranges.stream().noneMatch(range -> inRange(number, range))) {
          invalidNumbers.add(number);
        }
      }
    }

    return invalidNumbers.stream()
      .reduce(Integer::sum)
      .get();
  }

  private List<Pair<Integer, Integer>> getValidRanges(List<String> input) {
    List<Pair<Integer, Integer>> ranges = new ArrayList<>();
    for(String line : input) {
      if(line.startsWith("your ticket") || StringUtils.isBlank(line)) {
        break;
      }

      String numbers = line.split(":")[1].trim();
      String firstGroup = numbers.split(" or ")[0].trim();
      String secondGroup = numbers.split(" or ")[1].trim();

      ranges.add(new ImmutablePair<>(Integer.parseInt(firstGroup.split("-")[0]), Integer.parseInt(firstGroup.split("-")[1])));
      ranges.add(new ImmutablePair<>(Integer.parseInt(secondGroup.split("-")[0]), Integer.parseInt(secondGroup.split("-")[1])));

    }
    return ranges;
  }

  private boolean inRange(int number, Pair<Integer, Integer> range) {
    return number >= range.getLeft() && number <= range.getRight();
  }

  private long part2() {
    List<String> input = FileReader.readToList("Day16");
    List<List<Integer>> validTickets = getValidTickets(input);
    Map<String, List<Pair<Integer, Integer>>> ranges = getRanges(input);
    List<List<Integer>> numbersByPosition = getNumbersByPosition(validTickets, ranges.size());
    Map<String, Set<Integer>> possiblePosition = new HashMap<>();

    for (int i = 0; i < numbersByPosition.size(); i++) {
      for (Map.Entry<String, List<Pair<Integer, Integer>>> entry : ranges.entrySet()) {
        if (numbersByPosition.get(i).stream().allMatch(number -> isNumberInRanges(number, entry.getValue()))) {
          possiblePosition.computeIfAbsent(entry.getKey(), k->new HashSet<>());
          possiblePosition.get(entry.getKey()).add(i);
        }
      }
    }

//    List<List<String>> results = new ArrayList<>();
//    fillPosition(possiblePosition, 0, new ArrayList<>(), results);

    Set<Integer> onlyOnePossiblePosition = new HashSet<>();

    for(int i = 0; i < 20; i++) {
      for(Map.Entry<String, Set<Integer>> entry : possiblePosition.entrySet()) {
        if(entry.getValue().size() == 1) {
          onlyOnePossiblePosition.add(entry.getValue().stream().findAny().get());
        }
      }

      for(Map.Entry<String, Set<Integer>> entry : possiblePosition.entrySet()) {
        if(entry.getValue().size() != 1) {
          entry.getValue().removeAll(onlyOnePossiblePosition);
        }
      }
    }

    List<Integer> yourTicket = getYourTicket(input);

    return possiblePosition.entrySet().stream()
      .filter(entry -> entry.getKey().startsWith("departure"))
      .map(entry -> entry.getValue().stream().findFirst().get())
      .map(yourTicket::get)
      .map(Long::valueOf)
      .reduce((a,b) -> a*b)
      .get();
  }

  private List<Integer> getYourTicket(List<String> input) {
    String line = "";
    int i = 0;
    while(!input.get(i).startsWith("your ticket")) {
      i++;
    }
    return Stream.of(input.get(i+1).split(",")).map(Integer::parseInt).collect(Collectors.toList());
  }

  private void fillPosition(Map<String, Set<Integer>> possiblePosition, int index, List<String> currentList, List<List<String>> results) {
    //grrrrr why this doesn't work????
    if(index == possiblePosition.size()) {
      results.add(currentList);
      return;
    }

    List<String> currentTags = getTagFromPossiblePosition(index, possiblePosition);

    if(currentTags.isEmpty()) {
      return;
    }
    else {
      for(String tag : currentTags) {
        if(currentList.contains(tag)) {
          continue;
        }

        List<String> newList = new ArrayList<>(currentList);
        newList.add(tag);
        fillPosition(possiblePosition, index + 1, newList, results);
      }
    }
  }

  private List<String> getTagFromPossiblePosition(Integer position, Map<String, Set<Integer>> possiblePosition) {
    List<String> results = new ArrayList<>();

    for(Map.Entry<String, Set<Integer>> entry : possiblePosition.entrySet()) {
      if(entry.getValue().contains(position)) {
        results.add(entry.getKey());
        entry.getValue().remove(position);
      }
    }
    return results;
  }

  private boolean isNumberInRanges(Integer number, List<Pair<Integer, Integer>> ranges) {
    return ranges.stream().anyMatch(range -> inRange(number, range));
  }

  private List<List<Integer>> getNumbersByPosition(List<List<Integer>> validTickets, int fields) {
    List<List<Integer>> results = new ArrayList<>();

    for(int i = 0; i < fields; i++) {
      results.add(new ArrayList<>());
    }

    for (List<Integer> validTicket : validTickets) {
      for (int j = 0; j < validTicket.size(); j++) {
        results.get(j).add(validTicket.get(j));
      }
    }

    return results;
  }

  private Map<String, List<Pair<Integer, Integer>>> getRanges(List<String> input) {
    Map<String, List<Pair<Integer, Integer>>> fields = new HashMap<>();
    for(String line : input) {
      if(line.startsWith("your ticket") || StringUtils.isBlank(line)) {
        break;
      }

      List<Pair<Integer, Integer>> ranges = new ArrayList<>();

      String fieldName = line.split(":")[0].trim();
      String numbers = line.split(":")[1].trim();
      String firstGroup = numbers.split(" or ")[0].trim();
      String secondGroup = numbers.split(" or ")[1].trim();

      ranges.add(new ImmutablePair<>(Integer.parseInt(firstGroup.split("-")[0]), Integer.parseInt(firstGroup.split("-")[1])));
      ranges.add(new ImmutablePair<>(Integer.parseInt(secondGroup.split("-")[0]), Integer.parseInt(secondGroup.split("-")[1])));

      fields.put(fieldName, ranges);
    }

    return fields;
  }

  private List<List<Integer>> getValidTickets(List<String> input) {
    List<Pair<Integer, Integer>> ranges = getValidRanges(input);

    List<List<Integer>>  validTickets = new ArrayList<>();

    boolean skip = true;

    for(String line : input) {
      if(line.startsWith("nearby tickets")) {
        skip = false;
        continue;
      }

      if(skip) {
        continue;
      }

      List<Integer> numbers = Stream.of(line.split(","))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

      boolean valid = true;
      for(int number : numbers) {
        if(ranges.stream().noneMatch(range -> inRange(number, range))) {
          valid = false;
          break;
        }
      }

      if(valid) {
        validTickets.add(numbers);
      }
    }

    return validTickets;
  }
}
