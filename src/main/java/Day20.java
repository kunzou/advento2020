import org.apache.commons.lang3.StringUtils;
import utilities.FileReader;

import java.util.*;

public class Day20 {
  public static void solve() {
    System.out.println(new Day20().part1());
  }

  private long part1() {
    List<Image> images = loadImages();

    List<List<Character>> allEdges = getAllEdges(images);

    return findCornerPieces(images, allEdges).stream()
      .map(Image::getId)
      .map(Long::valueOf)
      .reduce((a,b) -> a*b)
      .get();
  }

  private List<List<Character>> getAllEdges(List<Image> images) {
    List<List<Character>> allEdges = new ArrayList<>();

    for(Image image : images) {
      allEdges.addAll(image.getEdges());
    }

    return allEdges;
  }

  private long countIdenticalEdge(List<Character> edge, List<List<Character>> allEdges) {
    return allEdges.stream()
      .filter(e -> identicalEdge(edge, e))
      .count();
  }

  private List<Image> findCornerPieces(List<Image> images, List<List<Character>> allEdges) {
    List<Image> cornerPieces = new ArrayList<>();

    for(Image image : images) {
      int edgeCount = 0;
      for(List<Character> edge : image.getEdges()) {
        if(countIdenticalEdge(edge, allEdges) == 1) {
          edgeCount++;
        }
      }
      if(edgeCount == 2) {
        cornerPieces.add(image);
      }
    }
    return cornerPieces;
  }

  private boolean identicalEdge(List<Character> characters1, List<Character> characters2) {
    if(characters1.equals(characters2)) {
      return true;
    }

    List<Character> temp = new ArrayList<>(characters1);

    Collections.reverse(temp);
    return temp.equals(characters2);
  }

  List<Image> loadImages() {
    List<String> input = FileReader.readToList("Day20");

    Image image = new Image();
    List<Image> images = new ArrayList<>();
    int row = -1;

    for(String line : input) {
      if(StringUtils.isBlank(line)) {
        continue;
      }

      if(line.startsWith("Tile")) {
        if(row != -1) {
          images.add(image);
        }
        image = new Image(Integer.parseInt(line.replaceAll("\\D+","")));
        row = 0;
        continue;
      }

      image.getMatrix()[row] = line.toCharArray();
      row++;
    }

    images.add(image);

    return images;
  }

  class Image {
    private int id;
    private char[][] matrix;

    public Image() {
    }

    public Image(int id) {
      this.id = id;
      this.matrix = new char[10][10];
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public char[][] getMatrix() {
      return matrix;
    }

    public void setMatrix(char[][] matrix) {
      this.matrix = matrix;
    }

    public List<Character> getTop() {
      List<Character> top = new ArrayList<>();
      for(Character c : matrix[0]) {
        top.add(c);
      }
      return top;
    }

    public List<Character> getBottom() {
      List<Character> bottom = new ArrayList<>();
      for(Character c : matrix[matrix.length-1]) {
        bottom.add(c);
      }
      return bottom;
    }

    public List<Character> getLeft() {
      List<Character> left = new ArrayList<>();
      for (char[] chars : matrix) {
        left.add(chars[0]);
      }
      return left;
    }

    public List<Character> getRight() {
      List<Character> right = new ArrayList<>();
      for (char[] chars : matrix) {
        right.add(chars[matrix.length - 1]);
      }
      return right;
    }

    public List<List<Character>> getEdges() {
      return Arrays.asList(getTop(), getBottom(), getLeft(), getRight());
    }
  }
}
