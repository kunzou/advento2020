import org.apache.commons.lang3.math.NumberUtils;
import utilities.FileReader;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {
  public static void solve() {
    System.out.println(new Day13().part2());
  }

  private int part1() {
    List<String> input = FileReader.readToList("Day13_input");
    return Stream.of(input.get(1).split(","))
      .filter(NumberUtils::isCreatable)
      .map(Integer::parseInt)
      .collect(Collectors.toMap(Function.identity(), route -> getWaitTime(route, Integer.parseInt(input.get(0)))))
      .entrySet()
      .stream()
      .min(Map.Entry.comparingByValue())
      .map(entry -> entry.getKey() * entry.getValue())
      .get();
  }

  private Integer getWaitTime(int route, int target) {
    int multiple = 0;
    int count = 1;
    while(multiple < target) {
      count++;
      multiple = route * count;
    }

    return multiple - target;
  }

  private long part2() {
    Map<Integer, Integer> sequence = getRoutesAndOrder(FileReader.readToList("Day13_input").get(1));

    long timestamp = 0;
    long leastCommonMultiple = 1;

    for(Map.Entry<Integer, Integer> routeAndSequence : sequence.entrySet()) {
      int route = routeAndSequence.getKey();
      int order = routeAndSequence.getValue();
      while((timestamp + order) % route != 0) {
        timestamp += leastCommonMultiple;
      }
      leastCommonMultiple = leastCommonMultiple * route;
    }

    return timestamp;
  }

  private Map<Integer, Integer> getRoutesAndOrder(String input) {
    List<String> tokens = Stream.of(input.split(",")).collect(Collectors.toList());

    return tokens.stream()
      .filter(NumberUtils::isCreatable)
      .collect(Collectors.toMap(Integer::parseInt, tokens::indexOf));
  }
}
