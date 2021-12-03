import java.util.List;
import utilities.FileReader;

public class Day2_2021 {

	public static int part1() {
		List<String> inputs = FileReader.readToList("Day2_2021");
		int x = 0;
		int y = 0;

		for(String input : inputs) {
			String command = input.split(" ")[0];
			int distance = Integer.parseInt(input.split(" ")[1]);

			if(command.equals("forward")) {
				x = x + distance;
			}
			if(command.equals("up")) {
				y = y - distance;
				if(y < 0) {
					y = 0;
				}
			}
			if(command.equals("down")) {
				y = y + distance;
			}
		}

		return x*y;
	}

	public static int part2() {
		List<String> inputs = FileReader.readToList("Day2_2021");
		int x = 0;
		int y = 0;
		int aim = 0;

		for(String input : inputs) {
			String command = input.split(" ")[0];
			int distance = Integer.parseInt(input.split(" ")[1]);

			if(command.equals("forward")) {
				x = x + distance;

				y = y + distance * aim;
			}
			if(command.equals("up")) {
				aim = aim - distance;
				if(aim < 0) {
					aim = 0;
				}
			}
			if(command.equals("down")) {
				aim += distance;
			}
		}

		return x*y;
	}
}
