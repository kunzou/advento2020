import utilities.FileReader;

public class Day2 {
  public int part1() {
    return (int) FileReader.readToList("resources/Day2_input").stream()
        .filter(this::isValidPart1)
        .count();
  }

  public int part2() {
    return (int)FileReader.readToList("resources/Day2_input").stream()
        .filter(this::isValidPart2)
        .count();
  }

  private boolean isValidPart1(String line) {
    String[] currentLine = line.split("\\s+");
    int lowerBound = Integer.parseInt(currentLine[0].split("-")[0]);
    int upperBound = Integer.parseInt(currentLine[0].split("-")[1]);
    Character target = currentLine[1].charAt(0);
    String phrase = currentLine[2];

    int count = 0;
    for(char c : phrase.toCharArray()) {
      if(c == target) {
        count++;
        if(count > upperBound) {
          return false;
        }
      }
    }

    return count >= lowerBound;
  }

  private boolean isValidPart2(String line) {
    String[] currentLine = line.split("\\s+");
    int position1 = Integer.parseInt(currentLine[0].split("-")[0]);
    int position2 = Integer.parseInt(currentLine[0].split("-")[1]);
    char target = currentLine[1].charAt(0);
    String phrase = currentLine[2];

    return phrase.charAt(position1-1) == target ^ phrase.charAt(position2-1) == target;
  }

}
