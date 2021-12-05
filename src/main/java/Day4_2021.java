import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utilities.FileReader;

public class Day4_2021 {
	public static void part1() {
//		List<Integer> numbers = Arrays.asList(27,14,70,7,85,66,65,57,68,23,33,78,4,84,25,18,43,71,76,61,34,82,93,74,26,15,83,64,2,35,19,97,32,47,6,51,99,20,77,75,56,73,80,86,55,36,13,95,52,63,79,72,9,10,16,8,69,11,50,54,81,22,45,1,12,88,44,17,62,0,96,94,31,90,39,92,37,40,5,98,24,38,46,21,30,49,41,87,91,60,48,29,59,89,3,42,58,53,67,28);
		List<Integer> numbers = Arrays.asList(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1);
//		List<String> inputs = FileReader.readToList("Day4_2021");
		List<String> inputs = FileReader.readToList("temp");

		List<String> card = new ArrayList<>();
		List<Card> cards = new ArrayList<>();
		for(String input : inputs) {
			if(!input.isEmpty()) {
				card.add(input.trim());
			}
			else {
				cards.add(new Card(card));
				card.clear();
			}
		}
		cards.add(new Card(card));

		for(int number : numbers) {
			cards.forEach(bingoCard -> bingoCard.draw(number));

			for(Card c : cards) {
				if(c.isWinner()) {
					c.calculate(number);
					return;
				}
			}

		}

	}

	public static void part2() {
		List<Integer> numbers = Arrays.asList(27,14,70,7,85,66,65,57,68,23,33,78,4,84,25,18,43,71,76,61,34,82,93,74,26,15,83,64,2,35,19,97,32,47,6,51,99,20,77,75,56,73,80,86,55,36,13,95,52,63,79,72,9,10,16,8,69,11,50,54,81,22,45,1,12,88,44,17,62,0,96,94,31,90,39,92,37,40,5,98,24,38,46,21,30,49,41,87,91,60,48,29,59,89,3,42,58,53,67,28);
		List<String> inputs = FileReader.readToList("Day4_2021");

		List<String> card = new ArrayList<>();
		List<Card> cards = new ArrayList<>();
		for(String input : inputs) {
			if(!input.isEmpty()) {
				card.add(input.trim());
			}
			else {
				cards.add(new Card(card));
				card.clear();
			}
		}
		cards.add(new Card(card));

		for(int number : numbers) {

			cards.forEach(bingoCard -> bingoCard.draw(number));

			if(cards.size() == 1 && cards.stream().allMatch(Card::isWinner)) {
				cards.forEach(c -> c.calculate(number));
			}

			cards.removeIf(Card::isWinner);
		}

	}

}

class Card {
	List<Set<Integer>> rows;
	List<Set<Integer>> columns;
	List<Set<Integer>> diagonals;

	public Card(List<String> inputs) {
		rows = new ArrayList<>();
		columns = new ArrayList<>();
		diagonals = new ArrayList<>();
		int[][] matrix = new int[5][5];
		int row = 0;
		for(String input : inputs) {
			String[] line = input.split("\\s+");
			for(int i = 0; i < line.length; i++) {
				matrix[row][i] = Integer.parseInt(line[i]);
			}
			row++;
		}

		for(int m = 0; m < 5; m++) {
			Set<Integer> rowSet = new HashSet<>();
			for(int n = 0; n < 5; n++) {
				rowSet.add(matrix[m][n]);
			}
			rows.add(rowSet);
		}

		for(int m = 0; m < 5; m++) {
			Set<Integer> columnSet = new HashSet<>();
			for(int n = 0; n < 5; n++) {
				columnSet.add(matrix[n][m]);
			}
			columns.add(columnSet);
		}

		Set<Integer> diagonalSet = new HashSet<>();
		for(int i = 0; i < 5; i++) {
			diagonalSet.add(matrix[i][i]);
		}
		diagonals.add(diagonalSet);

		diagonalSet = new HashSet<>();
		for(int i = 4; i >= 0; i--) {
			diagonalSet.add(matrix[4-i][i]);
		}
		diagonals.add(diagonalSet);
	}

	public List<Set<Integer>> getRows() {
		return rows;
	}

	public void setRows(List<Set<Integer>> rows) {
		this.rows = rows;
	}

	public List<Set<Integer>> getColumns() {
		return columns;
	}

	public void setColumns(List<Set<Integer>> columns) {
		this.columns = columns;
	}

	public List<Set<Integer>> getDiagonals() {
		return diagonals;
	}

	public boolean isWinner() {
		return rows.stream().anyMatch(Set::isEmpty) || columns.stream().anyMatch(Set::isEmpty) /*|| diagonals.stream().anyMatch(Set::isEmpty)*/;
	}

	public void draw(Integer number) {
		rows.forEach(set -> set.remove(number));
		columns.forEach(set -> set.remove(number));
		diagonals.forEach(set -> set.remove(number));
	}

	public void calculate(int currentNumber) {
		Set<Integer> numbers = new HashSet<>();

		rows.forEach(numbers::addAll);
		columns.forEach(numbers::addAll);
//		diagonals.forEach(numbers::addAll);

		System.out.println(numbers.stream().reduce(0, Integer::sum) * currentNumber);

	}
}
