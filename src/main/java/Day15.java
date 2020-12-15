import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day15 {

  public static void solve() {
    System.out.println(new Day15().part2());
  }

  private int part1() {
    LinkedList<Integer> queue = new LinkedList<>(Arrays.asList(16,11,15,0,1,7));

    int turn = 7;
    int lastNumber;
    int steps;

    while(turn <= 30000000) {
      LinkedList<Integer> tempQueue = new LinkedList<>(queue);
      lastNumber = tempQueue.removeLast();
      steps = 1;

      if(!tempQueue.contains(lastNumber)) {
        queue.add(0);
      }
      else {
        while(tempQueue.removeLast() != lastNumber) {
          steps++;
        }
        queue.add(steps);
      }

      turn++;
      System.out.println(turn);
    }

    return queue.peekLast();
  }

  private long part2() {
    List<Integer> list = new ArrayList<>(Arrays.asList(16, 11, 15, 0, 1, 7));

    Map<Integer, Integer> record = list.stream().collect(Collectors.toMap(Function.identity(), list::indexOf));

    int next;
    int turn = list.size() + 1;
    list.add(0);

    while (turn < 30000000) {
      int lastNumber = list.get(list.size() - 1);

      if (record.containsKey(lastNumber)) {
        next = turn - 1 - record.get(lastNumber);
        record.put(list.get(list.size() - 1), turn - 1);
        list.add(next);
      } else {
        list.add(0);
        record.put(list.get(list.size() - 2), turn - 1);
      }

      turn++;
    }

    return list.get(list.size() - 1);
  }
}
