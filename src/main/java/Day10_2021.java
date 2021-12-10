import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.common.collect.ImmutableMap;
import utilities.FileReader;

public class Day10_2021 {

	public static void part1() {
		List<String> inputs = FileReader.readToList("Day10_2021");

		Map<Character, Long> points = ImmutableMap.of(
				')', 3L,
				']', 57L,
				'}', 1197L,
				'>', 25137L
		);

		Map<Character, Character> pairs = ImmutableMap.of(
				'(', ')',
				'[', ']',
				'{', '}',
				'<', '>'
		);

		Set<Character> forwards = Stream.of('(', '[', '{', '<').collect(Collectors.toSet());

		long total = 0;
		for(String input : inputs) {
			Stack<Character> stack = new Stack<>();

			for(Character c : input.toCharArray()) {
				if(forwards.isEmpty() || forwards.contains(c)) {
					stack.push(c);
				}
				else {
					Character top = stack.pop();
					if(c != pairs.get(top)) {
						total+=points.get(c);
						break;
					}
				}

			}
		}

		System.out.println(total);

	}

	public static void part2() {
		List<String> inputs = FileReader.readToList("Day10_2021");

		Map<Character, Long> points = ImmutableMap.of(
				'(', 1L,
				'[', 2L,
				'{', 3L,
				'<', 4L
		);

		Map<Character, Character> pairs = ImmutableMap.of(
				'(', ')',
				'[', ']',
				'{', '}',
				'<', '>'
		);

		Set<Character> forwards = Stream.of('(', '[', '{', '<').collect(Collectors.toSet());

		List<Long> scores = new ArrayList<>();
		for(String input : inputs) {
			Stack<Character> stack = new Stack<>();
			boolean bad = false;
			for(Character c : input.toCharArray()) {
				if(forwards.isEmpty() || forwards.contains(c)) {
					stack.push(c);
				}
				else {
					Character top = stack.pop();
					if(c != pairs.get(top)) {
						bad = true;
						break;
					}
				}
			}

			long lineTotal = 0;
			while(!stack.isEmpty() && !bad) {
				Character current = stack.pop();
				lineTotal = lineTotal*5+points.get(current);
			}
			if(lineTotal>0) {
				scores.add(lineTotal);
			}
		}

		System.out.println(scores.stream().sorted().collect(Collectors.toList()).get(scores.size()/2));

	}
}
