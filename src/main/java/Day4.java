import utilities.FileReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {
  public static void solve() {
//    System.out.println(new Day4().part1());
    System.out.println(new Day4().part2());
  }

  private int part1() {
    List<String> rows = FileReader.readToListSeparatedByEmptyLine("Day4_input");
    int count = 0;
    for(String row : rows) {
      String[] fields = row.split("\\s+");
      Map<String, String> passport = new HashMap<>();
      for(String field : fields) {
        passport.put(field.split(":")[0], field.split(":")[1]);
      }
      if(isValidPassport(passport)) {
        count++;
      }
    }

    return count;
  }

  private int part2() {
    List<String> rows = FileReader.readToListSeparatedByEmptyLine("Day4_input");
    int count = 0;
    for(String row : rows) {
      String[] fields = row.split("\\s+");
      Map<String, String> passport = new HashMap<>();
      for(String field : fields) {
        passport.put(field.split(":")[0], field.split(":")[1]);
      }
      if(isValidPassport(passport)
        && validBYR(passport.get("byr"))
        && validECL(passport.get("ecl"))
        && validIYR(passport.get("iyr"))
        && validEYR(passport.get("eyr"))
        && validHGT(passport.get("hgt"))
        && validHCL(passport.get("hcl"))
        && validPID(passport.get("pid"))) {
          count++;
      }
    }

    return count;
  }

  private boolean isValidPassport(Map<String, String> passport) {
    if(passport.size() == 8) {
      return true;
    }
    if(passport.size() == 7 && !passport.containsKey("cid")) {
      return true;
    }

    return false;
  }

  private boolean validBYR(String field) {
    int year = Integer.parseInt(field);
    return year >= 1920 && year <= 2002;
  }

  private boolean validIYR(String field) {
    int year = Integer.parseInt(field);
    return year >= 2010 && year <= 2020;
  }

  private boolean validEYR(String field) {
    int year = Integer.parseInt(field);
    return year >= 2020 && year <= 2030;
  }

  private boolean validHGT(String field) {
    String unit = field.substring(Math.max(field.length() - 2, 0));
    int height = Integer.parseInt(field.substring(0, field.length() - 2));

    if("cm".equals(unit)) {
      return height >= 150 && height <= 193;
    }
    else {
      return height >= 59 && height <= 76;
    }
  }

  private boolean validHCL(String field) {
    if(field.charAt(0) !='#') {
      return false;
    }

    if(field.length() != 7) {
      return false;
    }

    String regex = "[0-9|a-f][0-9|a-f][0-9|a-f][0-9|a-f][0-9|a-f][0-9|a-f]";
    return field.substring(1).matches(regex);
  }

  private boolean validECL(String field) {
    return Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(field);
  }

  private boolean validPID(String field) {
    String regex = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
    return field.matches(regex);

  }
}
