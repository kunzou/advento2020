import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utilities.FileReader;

public class Day8_2021 {
	public static void part1() {
		List<String> inputs = FileReader.readToList("Day8_2021");

		int count = 0;
		for(String input : inputs) {
			String whatICare = input.split(" \\| ")[1];
			for(String word : whatICare.split(" ")) {
				if(word.length() == 2 || word.length() == 4 || word.length() == 3 || word.length() == 7) {
					count++;
				}
			}
		}
		System.out.println(count);
	}

	public static void part2() {
//		List<String> inputs = FileReader.readToList("Day8_2021");
		List<String> inputs = FileReader.readToList("temp");

		long total = 0;
		for(String input : inputs) {
			boolean valid = false;

			Set<Map<Character, Character>> tried = new HashSet<>();

			Map<Character, Character> dictionary = new HashMap<>();
			String first = input.split(" \\| ")[0];
			String second = input.split(" \\| ")[1];

//			List<String> words = new ArrayList<>(Arrays.asList(first.split(" ")));
			StringBuilder thisNumber = new StringBuilder();
			do {
				List<String> currentlist = new ArrayList<>(Arrays.asList(first.split(" ")));
				Collections.shuffle(currentlist);
				dictionary = new HashMap<>();
				for (String word : currentlist) {
					if (word.length() == 2) {
						dictionary.put(word.charAt(0), 'c');
						dictionary.put(word.charAt(1), 'f');
					}
					else if (word.length() == 3) {
						dictionary.put(word.charAt(0), 'a');
						dictionary.put(word.charAt(1), 'c');
						dictionary.put(word.charAt(2), 'f');
					}
					else if (word.length() == 4) {
						dictionary.put(word.charAt(0), 'b');
						dictionary.put(word.charAt(1), 'c');
						dictionary.put(word.charAt(2), 'd');
						dictionary.put(word.charAt(3), 'f');
					}
					else if (word.length() == 7) {
						dictionary.put(word.charAt(0), 'a');
						dictionary.put(word.charAt(1), 'b');
						dictionary.put(word.charAt(2), 'c');
						dictionary.put(word.charAt(3), 'd');
						dictionary.put(word.charAt(4), 'e');
						dictionary.put(word.charAt(5), 'f');
						dictionary.put(word.charAt(6), 'g');
					}
					if(dictionary.size() == 7 && dictionary.values().stream().distinct().count() == 7) {
						break;
					}
				}
				if (dictionary.size() != 7 || dictionary.values().stream().distinct().count() != 7) {
					continue;
				}

				if(tried.contains(dictionary)) {
					continue;
				}
				else {
					tried.add(dictionary);
				}

				int digit = 0;
				int number = -1;
				thisNumber = new StringBuilder();
				for (String word : second.split(" ")) {
					number = -1;
					if (word.length() == 2) {
						number = 1;
					} else if (word.length() == 4) {
						number = 4;
					} else if (word.length() == 3) {
						number = 7;
					} else if (word.length() == 7) {
						number = 8;
					} else {
						StringBuilder guess = new StringBuilder();
						for (Character c : word.toCharArray()) {
							guess.append(dictionary.get(c));
						}
						switch (guess.toString()) {
							case "abcefg":
								number = 0;
								break;
							case "abdefg":
								number = 6;
								break;
							case "acdeg":
								number = 2;
								break;
							case "acdfg":
								number = 3;
								break;
							case "abdfg":
								number = 5;
								break;
							case "abcdfg":
								number = 9;
								break;
						}
					}
					if(number == -1) {
						valid = false;
						break;
					}

					thisNumber.append(number);
					valid = true;
				}
			} while(!valid);

			total+=Integer.parseInt(thisNumber.toString());

		}

		System.out.println(total);

	}

	Map<Character, Character> getMap(String input) {
		Map<Character, Character> dictionary = new HashMap<>();
		String first = input.split(" \\| ")[0];
		String second = input.split(" \\| ")[1];

		List<String> words = new ArrayList<>(Arrays.asList(first.split(" ")));
		words.addAll(Arrays.asList(second.split(" ")));

		while (true) {

			List<String> currentlist = new ArrayList<>(Arrays.asList(first.split(" ")));
			Collections.shuffle(currentlist);
			dictionary = new HashMap<>();
			for(String word : currentlist) {
				if(word.length() == 2) {
					dictionary.put(word.charAt(0), 'c');
					dictionary.put(word.charAt(1), 'f');
				}
				if(word.length() == 3) {
					dictionary.put(word.charAt(0), 'a');
					dictionary.put(word.charAt(1), 'c');
					dictionary.put(word.charAt(2), 'f');
				}
				if(word.length() == 4) {
					dictionary.put(word.charAt(0), 'b');
					dictionary.put(word.charAt(1), 'c');
					dictionary.put(word.charAt(2), 'd');
					dictionary.put(word.charAt(3), 'f');
				}
				if(word.length() == 7) {
					dictionary.put(word.charAt(0), 'a');
					dictionary.put(word.charAt(1), 'b');
					dictionary.put(word.charAt(2), 'c');
					dictionary.put(word.charAt(3), 'd');
					dictionary.put(word.charAt(4), 'e');
					dictionary.put(word.charAt(5), 'f');
					dictionary.put(word.charAt(6), 'g');
				}
			}
			if(dictionary.size() == 7 && dictionary.values().stream().distinct().count() == 7) {
				break;
			}
		}
		return dictionary;
	}

}
