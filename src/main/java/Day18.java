import utilities.FileReader;

import java.util.List;
import java.util.Stack;

public class Day18 {
  public static void solve() {
    System.out.println(new Day18().part2());
  }

  public long part1() {
    List<String> input = FileReader.readToList("Day18");

    return input.stream()
      .map(this::calculate)
      .reduce(Long::sum)
      .get();
  }

  private long calculate(String line) {
    long result = 0;
    Stack<Long> resultStack = new Stack<>();
    Stack<Character> symbolStack = new Stack<>();
    char symbol = '+';
    for(int i = 0; i < line.length(); i++) {
      if(line.charAt(i) == ' ') {
        continue;
      }
      if(isNumber(line.charAt(i))) {
        result = calculate(result, line.charAt(i) - '0', symbol);
      }
      else if(line.charAt(i) == '(') {
        resultStack.push(result);
        symbolStack.push(symbol);
        result = 0;
        symbol = '+';
      }
      else if(line.charAt(i) == ')') {
        result = calculate(resultStack.pop(), result, symbolStack.pop());
      }
      else {
        symbol = line.charAt(i);
      }

    }

    return result;
  }

  private long calculate(long left, long right, Character symbol) {
    switch(symbol) {
      case '+':
        return left+right;
      case '-':
        return left-right;
      case '*':
        return left*right;
    }
    return Integer.MAX_VALUE;
  }

  private boolean isNumber(char c) {
    return c >= '0' && c <= '9';
  }

  private long part2() {
    List<String> input = FileReader.readToList("Day18");

    return input.stream()
      .map(this::calculatePart2)
      .reduce(Long::sum)
      .get();
  }

  public long calculatePart2(String line) {
    Stack<Character> operatorStack = new Stack<>();
    Stack<Long> numberStack = new Stack<>();

    for(int i = 0; i < line.length(); i++) {
      if(line.charAt(i) == ' ') {
        continue;
      }
      if(isNumber(line.charAt(i))) {
        numberStack.push((long)(line.charAt(i)-'0'));
      }
      else {
        char operator = line.charAt(i);
        if(operatorStack.isEmpty()) {
          operatorStack.push(operator);
        }
        else if(operator == '+' || operator == '-') {
          char lastOperator = operatorStack.peek();
          if(lastOperator == '(') {
            operatorStack.push(operator);
          }
          else if(lastOperator == '*' ) {
            operatorStack.push(operator);
          }
          else if(lastOperator == '+' || lastOperator == '-') {
            calculate(numberStack, operatorStack);
            operatorStack.push(operator);
          }
        }
        else if(operator == '*' ) {
          char lastOperator = operatorStack.peek();
          if(lastOperator != '(') {
            calculate(numberStack, operatorStack);
          }
          operatorStack.push(operator);
        }
        else if(operator == '(') {
          operatorStack.push(operator);
        }
        else if(operator == ')') {
          while (operatorStack.peek() != '(') {
            calculate(numberStack, operatorStack);
          }
          operatorStack.pop();
        }
      }
    }

    while (!operatorStack.isEmpty()) {
      calculate(numberStack, operatorStack);
    }

    return numberStack.peek();
  }

  private void calculate(Stack<Long> resultStack, Stack<Character> operatorStack) {
    long result = calculate(resultStack.pop(), resultStack.pop(), operatorStack.pop());
    resultStack.push(result);
  }
}

