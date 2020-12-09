import utilities.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day9 {
  public static void solve() {
    System.out.println(new Day9().part2());
  }

  private Long part1() {
    List<Long> numbers = FileReader.readToList("Day9_input").stream()
      .map(Long::parseLong)
      .collect(Collectors.toList());

    Queue<Long> queue = new LinkedList<>();

    for (int i = 0; i < 25; i++) {
      queue.add(numbers.get(i));
    }

    for (int i = 25; i < numbers.size(); i++) {
      if(!hasSum(queue, numbers.get(i))) {
        return numbers.get(i);
      }
      queue.remove();
      queue.add(numbers.get(i));
    }

    return null;
  }

  private boolean hasSum(Queue<Long> queue, Long sum) {
    Set<Long> numbers = new HashSet<>();

    for(long number : queue) {
      if(numbers.contains(sum - number)) {
        return true;
      }
      numbers.add(number);
    }

    return false;
  }

  public Long part2() {
    Long target = 375054920L;
    List<Long> numbers = FileReader.readToList("Day9_input").stream()
      .map(Long::parseLong)
      .collect(Collectors.toList());

    for (int i = 0; i < numbers.size(); i++) {
      for(int j = i+1; j < numbers.size(); j++) {
        List<Long> range = numbers.subList(i, j);
        Long sum = getSum(range);
        if(sum.equals(target)) {
          Long max = range.stream().max(Comparator.comparing(a->a)).get();
          Long min = range.stream().min(Comparator.comparing(a->a)).get();

          return min+max;
        }
        else if(sum > target) {
          break;
        }
      }
    }

    return null;
  }

  private Long getSum(List<Long> numbers) {
    return numbers.stream().reduce(0L, Long::sum);
  }
}
