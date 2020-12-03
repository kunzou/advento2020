import utilities.FileReader;

import java.util.List;

public class Day3 {
  public static void solve() {
    System.out.println(new Day3().part1());
    System.out.println(new Day3().part2());
  }

  private int part1() {
    int x = 0;
    int y = 0;
    List<String> rows = FileReader.readToList("Day3_input");
    int count = 0;
    while(y < rows.size()) {
      x += 3;

      if(x >= rows.get(y).length()) {
        x -= rows.get(y).length();
      }

      y++;

      if(y == rows.size()) {
        break;
      }

      if(rows.get(y).charAt(x) == '#') {
        count ++;
      }
    }

    return count;
  }

  private long part2() {
    return part2Helper(1,1)
        * part2Helper(3,1)
        * part2Helper(5,1)
        * part2Helper(7,1)
        * part2Helper(1,2);
  }

  private long part2Helper(int right, int down) {
    int x = 0;
    int y = 0;
    List<String> rows = FileReader.readToList("Day3_input");
    long count = 0;
    while(y < rows.size()) {
      x += right;

      if(x >= rows.get(y).length()) {
        x -= rows.get(y).length();
      }

      y += down;

      if(y >= rows.size()) {
        break;
      }

      if(rows.get(y).charAt(x) == '#') {
        count ++;
      }
    }

    return count;
  }
}
