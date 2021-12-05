import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import utilities.FileReader;

public class Day5_2021 {

	public static void part1() {
		List<String> inputs = FileReader.readToList("Day5_2021");

		Map<Point, Integer> count = new HashMap<>();

		for(String input : inputs) {
			String point1 = input.split(" -> ")[0];
			String point2 = input.split(" -> ")[1];
			int x1 = Integer.parseInt(point1.split(",")[0]);
			int y1 = Integer.parseInt(point1.split(",")[1]);
			int x2 = Integer.parseInt(point2.split(",")[0]);
			int y2 = Integer.parseInt(point2.split(",")[1]);

			Line line = new Line(new Point(x1, y1), new Point(x2, y2));
			for(Point point : line.getPoints()) {
				count.putIfAbsent(point, 0);
				count.computeIfPresent(point, (k,v) -> v+1);
			}
		}

		System.out.println(
				count.values().stream()
						.filter(integer -> integer > 1)
						.count()
		);

	}

	public static void part2() {
		List<String> inputs = FileReader.readToList("Day5_2021");

		Map<Point, Integer> count = new HashMap<>();

		for(String input : inputs) {
			String point1 = input.split(" -> ")[0];
			String point2 = input.split(" -> ")[1];
			int x1 = Integer.parseInt(point1.split(",")[0]);
			int y1 = Integer.parseInt(point1.split(",")[1]);
			int x2 = Integer.parseInt(point2.split(",")[0]);
			int y2 = Integer.parseInt(point2.split(",")[1]);

			Line line = new Line(new Point(x1, y1), new Point(x2, y2));
			for(Point point : line.getPoints()) {
				count.putIfAbsent(point, 0);
				count.computeIfPresent(point, (k,v) -> v+1);
			}
		}

		System.out.println(
				count.values().stream()
						.filter(integer -> integer > 1)
						.count()
		);

	}
}

class Line {
	Set<Point> points;

	public Line(Point a, Point b) {
		points = new HashSet<>();
		if(a.sameRow(b)) {
			addPointsSameRow(a, b);
		}
		if(a.sameColumn(b)) {
			addPointsSameColumn(a, b);
		}
//		part2
		if(a.sameForwardDiagonal(b)) {
			addPointsSameForwardDiagonal(a, b);
		}
//		part2
		if(a.sameBackwardDiagonal(b)) {
			addPointsSameBackwardDiagonal(a, b);
		}
	}

	private void addPointsSameRow(Point a, Point b) {
		Point start = a.getStartingPoint(b);
		Point end = start.equals(a) ? b : a;

		for(int i = start.getX(); i <= end.getX(); i++) {
			points.add(new Point(i, a.getY()));
		}
	}

	private void addPointsSameColumn(Point a, Point b) {
		Point start = a.getStartingPoint(b);
		Point end = start.equals(a) ? b : a;

		for(int i = start.getY(); i <= end.getY(); i++) {
			points.add(new Point(a.getX(), i));
		}
	}

	private void addPointsSameForwardDiagonal(Point a, Point b) {
		Point start = a.getStartingPoint(b);
		Point end = start.equals(a) ? b : a;

		int steps = end.getX() - start.getX();
		for (int i = 0; i <= steps; i++) {
			points.add(new Point(start.getX() + i, start.getY() + i));
		}
	}

	private void addPointsSameBackwardDiagonal(Point a, Point b) {
		Point start = a.getStartPointBackwardDiagonal(b);
		Point end = start.equals(a) ? b : a;

		int steps = end.getX() - start.getX();
		for (int i = 0; i <= steps; i++) {
			points.add(new Point(start.getX() + i, start.getY() - i));
		}
	}

	public Set<Point> getPoints() {
		return points;
	}
}

class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean sameRow(Point anotherPoint) {
		return y == anotherPoint.getY();
	}

	public boolean sameColumn(Point anotherPoint) {
		return x == anotherPoint.getX();
	}

	public boolean sameForwardDiagonal(Point anotherPoint) {
		return x - anotherPoint.getX() == y - anotherPoint.getY();
	}

	public boolean sameBackwardDiagonal(Point anotherPoint) {
		return x - anotherPoint.getX() == anotherPoint.getY() - y;
	}

	public boolean onTheLeftOf(Point anotherPoint) {
		return x < anotherPoint.getX();
	}

	public boolean above(Point anotherPoint) {
		return y < anotherPoint.getY();
	}

	public Point getStartingPoint(Point anotherPoint) {
		return (onTheLeftOf(anotherPoint) || above(anotherPoint)) ? this :anotherPoint;
	}

	public Point getStartPointBackwardDiagonal(Point anotherPoint) {
		return onTheLeftOf(anotherPoint) ? this : anotherPoint;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point point = (Point) o;
		return x == point.x && y == point.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}

