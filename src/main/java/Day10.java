import utilities.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
  public static void solve() {
    System.out.println(new Day10().part2());
  }

  private int part1() {
    List<Integer> numbers = FileReader.readToList("Day10_input").stream()
      .map(Integer::parseInt)
      .sorted()
      .collect(Collectors.toList());

    int count1 = 0;
    int count3 = 0;
    int current = 0;
    int difference;
    for(int number : numbers) {
      difference = number - current;

      if(difference == 1) {
        count1++;
      }
      else if(difference == 3) {
        count3++;
      }
      current = number;
    }

    return count1*(count3+1);
  }

  private long part2() {
    List<Integer> current = new ArrayList<>();
    current.add(0);
    List<Integer> numbers = FileReader.readToList("Day10_input").stream()
      .map(Integer::parseInt)
      .sorted()
      .collect(Collectors.toList());
    Long[] cache = new Long[numbers.size()];

    return steps(numbers, numbers.size()-1, cache);
  }

  private long steps(List<Integer> numbers, int index, Long[] cache) {
    if(index <= 0) {
      return 1L;
    }

    if(cache[index] != null) {
      return cache[index];
    }

    long currentNumber = numbers.get(index);
    long count=0;

    if(currentNumber - numbers.get(index-1) <= 3) {
      count+=steps(numbers, index-1, cache);
    }

    if(currentNumber ==2 || currentNumber - numbers.get(index-2) <= 3) {
      count+=steps(numbers, index-2, cache);
    }

    if(currentNumber ==3 || (index >2 && currentNumber - numbers.get(index-3) <= 3)) {
      count+=steps(numbers, index-3, cache);
    }

    cache[index] = count;
    return count;
  }
}
