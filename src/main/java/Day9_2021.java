import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import utilities.FileReader;

public class Day9_2021 {
	public static void part1() {
		List<String> inputs = FileReader.readToList("Day9_2021");

		int maxRow = inputs.size();
		int maxColumn = inputs.get(0).length();
		int[][] DIRECTIONS = {{0,1},{0,-1},{1,0},{-1,0}};
		int[][] map = new int[maxRow][maxColumn];
		long total = 0;
		for(int row = 0; row < maxRow; row++) {
			for(int column = 0; column < maxColumn; column++) {
				map[row][column] = inputs.get(row).charAt(column) - '0';
			}
		}

		for (int m = 0; m < maxRow; m++) {
			for (int n = 0; n < maxColumn; n++) {
				boolean lowest = true;
				for(int[] dir : DIRECTIONS) {
					int row = m+dir[0];
					int col = n+dir[1];

					if(row < 0 || row > maxRow-1 || col < 0 || col > maxColumn-1) {
						continue;
					}

					if(map[m][n] >= map[row][col]) {
						lowest = false;
						break;
					}
				}
				if(lowest) {
					total+=(map[m][n]+1L);
					System.out.println(map[m][n]+1);
				}

			}
		}

		System.out.println(total);
	}
	public static void part2() {
		List<String> inputs = FileReader.readToList("Day9_2021");

		int maxRow = inputs.size();
		int maxColumn = inputs.get(0).length();

		int[][] map = new int[maxRow][maxColumn];
		List<Integer> sizes = new ArrayList<>();
		boolean taken[][] = new boolean[maxRow][maxColumn];
		for(int row = 0; row < maxRow; row++) {
			for(int column = 0; column < maxColumn; column++) {
				map[row][column] = inputs.get(row).charAt(column) - '0';
			}
		}

		for (int m = 0; m < maxRow; m++) {
			for (int n = 0; n < maxColumn; n++) {
				int size = discover(map, m, n, taken);
				if(size > 0) {
					sizes.add(size);
				}
			}
		}

		System.out.println(sizes.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(1, (a,b) -> a*b));
	}

	static int discover(int[][] map, int row, int column, boolean[][] taken) {
		if(taken[row][column] || map[row][column] == 9) {
			return 0;
		}
		taken[row][column] = true;
		int[][] DIRECTIONS = {{0,1},{0,-1},{1,0},{-1,0}};
		int count = 1;
		for(int[] dir : DIRECTIONS) {
			int nextRow = row+dir[0];
			int nextCol = column+dir[1];


			if(nextRow < 0 || nextRow > map.length-1 || nextCol < 0 || nextCol > map[0].length-1 || taken[nextRow][nextCol]) {
				continue;
			}

			count += discover(map, nextRow, nextCol, taken);
		}
		return count;
	}
}
