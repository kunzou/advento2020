import utilities.FileReader;

import java.util.List;

public class Day12 {
  public static void solve() {
    System.out.println(new Day12().part2());
  }

  private int part1() {
    List<String> input = FileReader.readToList("Day12_input");

    Location location = new Location(0,0);

    for(String line : input) {
      Motion motion = parse(line);

      if(motion.getMove() == 'N') {
        location.moveNorth(motion.getUnit());
      }

      if(motion.getMove() == 'S') {
        location.moveSouth(motion.getUnit());
      }

      if(motion.getMove() == 'W') {
        location.moveWest(motion.getUnit());
      }

      if(motion.getMove() == 'E') {
        location.moveEast(motion.getUnit());
      }

      if(motion.getMove() == 'F') {
        location.moveForward(motion.getUnit());
      }

      if(motion.getMove() == 'L') {
        location.turnLeft(motion.getUnit());
      }

      if(motion.getMove() == 'R') {
        location.turnRight(motion.getUnit());
      }

    }

    return Math.abs(location.getX())+ Math.abs(location.getY());
  }

  private int part2() {
    List<String> input = FileReader.readToList("Day12_input");

    Location waypoint = new Location(10,1);
    Location location = new Location(0,0);

    for(String line : input) {
      Motion motion = parse(line);

      if(motion.getMove() == 'N') {
        waypoint.moveNorth(motion.getUnit());
      }

      if(motion.getMove() == 'S') {
        waypoint.moveSouth(motion.getUnit());
      }

      if(motion.getMove() == 'W') {
        waypoint.moveWest(motion.getUnit());
      }

      if(motion.getMove() == 'E') {
        waypoint.moveEast(motion.getUnit());
      }

      if(motion.getMove() == 'F') {
        location.move(waypoint, motion.getUnit());
      }

      if(motion.getMove() == 'L') {
        waypoint.waypointLeft(motion.getUnit());
      }

      if(motion.getMove() == 'R') {
        waypoint.waypointRight(motion.getUnit());
      }

    }


    return Math.abs(location.getX())+ Math.abs(location.getY());
  }

  private Motion parse(String line) {
    return new Motion(line.charAt(0), Integer.parseInt(line.substring(1)));
  }

  class Location {
    private int x;
    private int y;

    private int[] direction;

    public Location(int x, int y) {
      this.x = x;
      this.y = y;
      direction = new int[]{1, 0};
    }

    public void move(Location waypoint, int unit) {
      x += waypoint.getX() * unit;
      y += waypoint.getY() * unit;
    }

    public void moveNorth(int unit) {
      y+=unit;
    }

    public void moveSouth(int unit) {
      y-=unit;
    }

    public void moveEast(int unit) {
      x+=unit;
    }

    public void moveWest(int unit) {
      x-=unit;
    }

    public void moveForward(int unit) {
      while(unit > 0) {
        x+=direction[0];
        y+=direction[1];
        unit--;
      }
    }

    public void turnLeft(int unit) {
      int dx = direction[0];
      int dy = direction[1];
      if(unit == 90) {
        direction[0] = -dy;
        direction[1] = dx;
      }

      if(unit == 180) {
        direction[0] = -dx;
        direction[1] = -dy;
      }

      if(unit == 270) {
        direction[0] = dy;
        direction[1] = -dx;
      }
    }

    public void waypointLeft(int unit) {
      int dx = x;
      int dy = y;
      if(unit == 90) {
        x = -dy;
        y = dx;
      }

      if(unit == 180) {
        x = -dx;
        y = -dy;
      }

      if(unit == 270) {
        x = dy;
        y = -dx;
      }
    }

    public void turnRight(int unit) {
      int dx = direction[0];
      int dy = direction[1];
      if(unit == 90) {
        direction[0] = dy;
        direction[1] = -dx;
      }

      if(unit == 180) {
        direction[0] = -dx;
        direction[1] = -dy;
      }

      if(unit == 270) {
        direction[0] = -dy;
        direction[1] = dx;
      }
    }
    public void waypointRight(int unit) {
      int dx = x;
      int dy = y;
      if(unit == 90) {
        x = dy;
        y = -dx;
      }

      if(unit == 180) {
        x = -dx;
        y = -dy;
      }

      if(unit == 270) {
        x = -dy;
        y = dx;
      }
    }

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int[] getDirection() {
      return direction;
    }

    public void setDirection(int[] direction) {
      this.direction = direction;
    }
  }

  class Motion {
    private Character move;
    private int unit;

    public Motion(Character move, int unit) {
      this.move = move;
      this.unit = unit;
    }

    public Character getMove() {
      return move;
    }

    public void setMove(Character move) {
      this.move = move;
    }

    public int getUnit() {
      return unit;
    }

    public void setUnit(int unit) {
      this.unit = unit;
    }
  }
}
