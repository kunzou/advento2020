package utilities;

import java.util.Arrays;

public class Utility {
  public static void printArray(char[][] array) {
    for(int i = 0; i < array.length; i++) {
      printArray(array[i]);
    }
    System.out.println();
  }

  public static void printArray(char[] array) {
    System.out.println(Arrays.toString(array));
    System.out.println();
  }
}
