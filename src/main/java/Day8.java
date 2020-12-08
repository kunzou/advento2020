import utilities.FileReader;

import java.util.List;

public class Day8 {
  public static void solve() {
    System.out.println(new Day8().part2());
  }

  private int part1() {
    List<String> instructions = FileReader.readToList("Day8_input");
    String command;
    int value;
    int accumulator = 0;
    Boolean[] visited = new Boolean[instructions.size()];
    int cursor = 0;
    while(visited[cursor] == null) {
      visited[cursor] = true;
      command = instructions.get(cursor).split("\\s+")[0];
      value = Integer.parseInt(instructions.get(cursor).split("\\s+")[1]);
      if(command.equals("acc")) {
        accumulator += value;
        cursor++;
      }
      else if(command.equals("nop")) {
        cursor++;
      }
      else if(command.equals("jmp")) {
        cursor += value;
      }
    }

    if(cursor == instructions.size()) {
      System.out.println();
    }

    return accumulator;
  }

  private int part2() {
    List<String> instructions = FileReader.readToList("Day8_input");
    String command;


    for(int i = 0; i < instructions.size(); i++) {
      int value;
      int accumulator = 0;
      Boolean[] visited = new Boolean[instructions.size()];
      int cursor = 0;

      String thisCommand = instructions.get(i).split("\\s+")[0];

      if(thisCommand.equals("acc")) {
        continue;
      }

      if(thisCommand.equals("jmp")) {
        instructions.set(i, "nop " + instructions.get(i).split("\\s+")[1]);
      }
      else if(thisCommand.equals("nop")) {
        instructions.set(i, "jmp " + instructions.get(i).split("\\s+")[1]);
      }

      while(visited[cursor] == null) {
        visited[cursor] = true;
        command = instructions.get(cursor).split("\\s+")[0];
        value = Integer.parseInt(instructions.get(cursor).split("\\s+")[1]);
        if(command.equals("acc")) {
          accumulator += value;
          cursor++;
        }
        else if(command.equals("nop")) {
          cursor++;
        }
        else if(command.equals("jmp")) {
          cursor += value;
        }

        if(cursor == instructions.size()) {
          return accumulator;
        }
      }

      instructions.set(i, thisCommand + " " + instructions.get(i).split("\\s+")[1]);
    }

    return -1;
  }
}
