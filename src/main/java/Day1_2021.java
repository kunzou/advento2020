import java.util.LinkedList;
import java.util.List;
import utilities.FileReader;

public class Day1_2021 {

	public static int part1() {
		List<String> inputs = FileReader.readToList("Day1_2021");
		int count = 0;
		int prev = -1;
		for(String input : inputs) {
			int number = Integer.parseInt(input);
			if(prev == -1) {
				prev = number;
				continue;
			}

			if(number > prev) {
				count ++;
			}
			prev = number;
		}

		return count;
	}

	public static int part2() {
		List<String> inputs = FileReader.readToList("Day1_2021");
		LinkedList<Integer> queue = new LinkedList<>();

		int count = 0;

		for(String input : inputs) {
			int number = Integer.parseInt(input);
			if(queue.size() < 3) {
				queue.add(number);
			}
			else {
				if(number > queue.removeFirst()) {
					count++;
				}

				queue.addLast(number);
			}
		}
		return count;
	}
}
