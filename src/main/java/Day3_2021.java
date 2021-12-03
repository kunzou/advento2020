import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utilities.FileReader;

public class Day3_2021 {

	public static void part1() {
		List<String> inputs = FileReader.readToList("Day3_2021");
		int[] counter = new int[12];
		for(String input : inputs) {
			for(int i = 0; i < input.length(); i++) {
				if(input.charAt(i) - '0' == 1 ) {
					counter[i]++;
				}
			}
		}

		for(int i=0 ; i< counter.length; i++) {
			if(counter[i] > inputs.size()/2) {
				System.out.print(1);
			}
			else {
				System.out.print(0);
			}
		}
	}

	public static void part2() {
		List<String> inputs = FileReader.readToList("Day3_2021");
		Set<String> oxygen = new HashSet<>(inputs);
		Set<String> co2 = new HashSet<>(inputs);
		Set<String> sub1 = new HashSet<>();
		Set<String> sub0 = new HashSet<>();

		int i = 0;
		while(oxygen.size() != 1) {
			sub1.clear();
			sub0.clear();
			for(String number : oxygen) {
				if(number.charAt(i) - '0' == 1) {
					sub1.add(number);
				}
				else {
					sub0.add(number);
				}
			}
			if(sub1.size() > sub0.size()) {
				oxygen = new HashSet<>(sub1);
			}
			else if (sub1.size() < sub0.size()){
				oxygen = new HashSet<>(sub0);
			}
			else {
				oxygen = new HashSet<>(sub1);
			}
			i++;
		}

		i = 0;
		while(co2.size() != 1) {
			sub1.clear();
			sub0.clear();
			for(String number : co2) {
				if(number.charAt(i) - '0' == 1) {
					sub1.add(number);
				}
				else {
					sub0.add(number);
				}
			}
			if(sub1.size() > sub0.size()) {
				co2 = new HashSet<>(sub0);
			}
			else if (sub1.size() < sub0.size()){
				co2 = new HashSet<>(sub1);
			}
			else {
				co2 = new HashSet<>(sub0);
			}
			i++;
		}

		System.out.println(oxygen);
		System.out.println(co2);
	}
}

