import utilities.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day17 {
  public static void solve() {
    System.out.println(new Day17().part1());
  }

  private int part1() {
    List<String> input = FileReader.readToList("Day17");

    Set<Cube> activeCubes = getCubes(input, '#');
    Set<Cube> inactiveCubes = getCubes(input, '.');

    int cycle = 0;

    while(cycle < 6) {
      for(Cube cube : activeCubes.stream().flatMap(cube -> cube.getNeighbors().stream()).collect(Collectors.toList())) {
        if(!activeCubes.contains(cube)) {
          inactiveCubes.add(cube);
        }
      }

      for(Cube cube : inactiveCubes.stream().flatMap(cube -> cube.getNeighbors().stream()).collect(Collectors.toList())) {
        if(!activeCubes.contains(cube)) {
          inactiveCubes.add(cube);
        }
      }

      Set<Cube> newActive = new HashSet<>();
      Set<Cube> newInactive = new HashSet<>();
      for(Cube cube : activeCubes) {
        long numberOfActiveNeighbor = cube.getNeighbors().stream()
          .filter(activeCubes::contains)
          .count();
        if(numberOfActiveNeighbor == 2 || numberOfActiveNeighbor == 3) {
          newActive.add(cube);
        }
        else {
          newInactive.add(cube);
        }
      }

      for(Cube cube : inactiveCubes) {
        long numberOfActiveNeighbor = cube.getNeighbors().stream()
          .filter(activeCubes::contains)
          .count();
        if(numberOfActiveNeighbor == 3) {
          newActive.add(cube);
        }
        else {
          newInactive.add(cube);
        }
      }
      activeCubes = newActive;
      inactiveCubes = newInactive;
      cycle++;
    }

    return activeCubes.size();
  }

  private Set<Cube> getCubes(List<String> input, Character state) {
    Set<Cube> cubes = new HashSet<>();
    for(int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        if (input.get(i).charAt(j) == state) {
          cubes.add(new Cube(j, i, 0));
        }
      }
    }
    return cubes;
  }
}

class Cube {
  private int x;
  private int y;
  private int z;

  public Cube(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
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

  public int getZ() {
    return z;
  }

  public void setZ(int z) {
    this.z = z;
  }

  public List<Cube> getNeighbors() {
    List<Cube> neighbors = new ArrayList<>();

    for(int x = -1; x <= 1; x++) {
      for(int y = -1; y <= 1; y++) {
        for(int z = -1; z <= 1; z++) {
          neighbors.add(new Cube(this.x+x, this.y+y, this.z+z));
        }
      }
    }

    neighbors.remove(this);

    return neighbors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Cube)) return false;
    Cube cube = (Cube) o;
    return getX() == cube.getX() && getY() == cube.getY() && getZ() == cube.getZ();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getX(), getY(), getZ());
  }
}
