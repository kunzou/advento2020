import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import utilities.FileReader;

import java.util.*;

public class Day5 {
  public static void solve() {
    System.out.println(new Day5().part2());
  }

  private int part1() {
    return FileReader.readToList("Day5_input").stream()
      .map(this::getSeatNumber)
      .max(Comparator.comparing(i->i))
      .get();
  }

  private int part2() {
    for(int i = 1; i < 127; i++) {
      for (int j = 0; j < 8; j++) {
        System.out.println(i + ", " + j); //copy and past this to a file called temp2
      }
    }

    //yourFavoriteDiffTool temp1 temp2
    //hola

    return -1;
  }

  private int getSeatNumber(String ticket) {
    MutablePair<Integer, Integer> row = new MutablePair<>(0, 127);
    MutablePair<Integer, Integer> column = new MutablePair<>(0, 7);
    for(int i = 0; i < 7; i++) {
      row = parseRowCode(ticket.charAt(i), row);
    }

    for (int i = 7; i <10; i++) {
      column = parseRowCode(ticket.charAt(i), column);
    }

    System.out.println(row.getLeft() + ", " + column.getLeft()); //copy and paste this to a file called temp1

    return row.getLeft()*8+column.getLeft();
  }



  private MutablePair<Integer, Integer> parseRowCode(Character code, Pair<Integer, Integer> bound) {
    if(code == 'F' || code == 'L') {
      return new MutablePair<>(bound.getLeft(), (bound.getRight()+bound.getLeft())/2);
    }
    else if(code == 'B' || code =='R') {
      return new MutablePair<>((bound.getRight()+bound.getLeft())/2+1, bound.getRight());
    }

    return null;
  }
}
