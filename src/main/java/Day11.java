import utilities.FileReader;

import java.util.Arrays;
import java.util.List;

public class Day11 {
  public static void solve() {
    System.out.println(new Day11().part2());
  }

  private int part1() {
    List<String> input = FileReader.readToList("Day11_input");

    char[][] matrix = convertMatrix(input);
    int maxRow = matrix.length;
    int maxColumn = matrix[0].length;
    int[][] DIRECTIONS = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,-1},{-1,1}};

    while(true) {
      char[][] copy = copy(matrix);
      for (int m = 0; m < maxRow; m++) {
        for (int n = 0; n < maxColumn; n++) {
          int countOccupied = 0;
          if(copy[m][n] == '.') {
            continue;
          }

          for(int[] dir : DIRECTIONS) {
            int row = m+dir[0];
            int col = n+dir[1];

            if(row < 0 || row > maxRow-1 || col < 0 || col > maxColumn-1) {
              continue;
            }

            if(copy[row][col] == '#') {
              countOccupied++;
            }
          }
          if(copy[m][n] == 'L' && countOccupied == 0) {
            matrix[m][n] = '#';
          }
          else if(copy[m][n] == '#' && countOccupied >= 4) {
            matrix[m][n] = 'L';
          }
        }
      }

      if(Arrays.deepEquals(copy, matrix)) {
        break;
      }
    }

    return countOccupied(matrix);
  }

  private char[][] copy(char[][] matrix) {
    char[][] copy = new char[matrix.length][matrix[0].length];
    for(int i=0; i<matrix.length; i++) {
      System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length);
    }

    return copy;
  }

  private int countOccupied(char[][] matrix) {
    int count = 0;
    for (char[] chars : matrix) {
      for (int n = 0; n < matrix[0].length; n++) {
        if (chars[n] == '#') {
          count++;
        }
      }
    }
    return count;
  }

  private char[][] convertMatrix(List<String> input) {
    char[][] matrix = new char[input.size()][input.get(0).length()];

    int m = 0;
    int n = 0;
    for(String line : input) {
      n = 0;
      for(char c : line.toCharArray()) {
        matrix[m][n] = c;
        n++;
      }
      m++;
    }

    return matrix;
  }

  private int part2() {
    List<String> input = FileReader.readToList("Day11_input");

    char[][] matrix = convertMatrix(input);
    int maxRow = matrix.length;
    int maxColumn = matrix[0].length;
    int[][] DIRECTIONS = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,-1},{-1,1}};

    while(true) {
      char[][] copy = copy(matrix);
      for (int m = 0; m < maxRow; m++) {
        for (int n = 0; n < maxColumn; n++) {
          int countOccupied = 0;
          if(copy[m][n] == '.') {
            continue;
          }

          for(int[] dir : DIRECTIONS) {
            int row = m;
            int col = n;

            while(!(row < 0 || row > maxRow-1 || col < 0 || col > maxColumn-1)) {
              row = row+dir[0];
              col = col+dir[1];

              if(row < 0 || row > maxRow-1 || col < 0 || col > maxColumn-1) {
                continue;
              }

              if(copy[row][col] == '#') {
                countOccupied++;
                break;
              }
              else if(copy[row][col] == 'L') {
                break;
              }
            }
          }
          if(copy[m][n] == 'L' && countOccupied == 0) {
            matrix[m][n] = '#';
          }
          else if(copy[m][n] == '#' && countOccupied >= 5) {
            matrix[m][n] = 'L';
          }
        }
      }

      if(Arrays.deepEquals(copy, matrix)) {
        break;
      }
    }

    return countOccupied(matrix);
  }
}
