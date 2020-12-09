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

    int left = 0;
    int right = 1;
    Long sum = numbers.get(left) + numbers.get(right);

    while(!sum.equals(target)) {
      if(sum < target) {
        right ++;
        sum += numbers.get(right);
      }
      else if(sum > target) {
        sum -= numbers.get(left);
        left ++;
      }
    }

    Long max = numbers.subList(left, right).stream().max(Comparator.comparing(a->a)).get();
    Long min = numbers.subList(left, right).stream().min(Comparator.comparing(a->a)).get();

    return min+max;
  }
}
