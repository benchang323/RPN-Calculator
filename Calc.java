package hw4;

// Import dependencies
import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {
  /**
   * The main function.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    // Create a scanner to take inputs
    Scanner sc = new Scanner(System.in);

    // Create a stack to hold integers
    LinkedStack<Integer> stack = new LinkedStack<>();

    // String to hold input and control flow
    String input = "";

    // Count to track number of integers in the stack
    int count = 0;

    // Program ends when "!" is entered
    while (!"!".equals(input) && sc.hasNext()) {
      // Get input from user
      input = sc.next();

      // Keep track of program input through different status
      int status = validation(input, stack, count);
      count = execute(status, stack, input, count);
    }
  }

  /**
   * Executes the main operations of the program.
   * @param status The status of the program from validation() where 0 if the input is an operator,
   *               1 if the input is invalid, 2 if the input is a stub (feature executed, go to next loop or
   *               terminate program), 3 if the input added to the stack.
   * @param stack The stack that holds the integer data.
   * @param input The input from the user.
   * @param count The number of integers in the stack.
   * @return The number of integers in the stack.
   */
  private static int execute(int status, LinkedStack<Integer> stack, String input, int count) {
    switch (status) {
      case 0: // Operators (+, -, *, /, %)
        // If there is only one integer in the stack or if stack is empty, print error and return
        if (!operandValid(count, 2)) {
          break;
        } else if (operation(input, stack) == 0) {
          --count;
        }
        break;
      case 1: // Invalid input
        System.out.println("ERROR: bad token");
        break;
      case 3: // Added integer to stack
        ++count;
        break;
      default: // Stub (execute next loop), Input: ?, ., integers
        break;
    }
    return count;
  }

  /**
   * Manipulates the integers per the operator.
   * @param input The operator to be executed.
   * @param stack The stack that holds the integer data.
   */
  private static int operation(String input, LinkedStack<Integer> stack) {
    // Get the top two integers from the stack
    int first = stack.top();
    // Check for division by zero
    if (first == 0 && ("/".equals(input) || "%".equals(input))) {
      System.out.println("ERROR: division by zero");
      return 1;
    }
    stack.pop();
    int second = stack.top();
    stack.pop();

    // Manipulate the integers per the operator
    pushOperation(input, stack, first, second);
    return 0;
  }

  /**
   * Push the result of the operation to the stack.
   * @param input The operator to be executed.
   * @param stack The stack that holds the integer data.
   * @param first The first integer to be manipulated.
   * @param second The second integer to be manipulated.
   */
  private static void pushOperation(String input, LinkedStack<Integer> stack, int first, int second) {
    if ("+".equals(input)) { // +
      stack.push(first + second);
    } else if ("-".equals(input)) { // -
      stack.push(second - first);
    } else if ("*".equals(input)) { // *
      stack.push(first * second);
    } else if ("/".equals(input)) { // /
      stack.push(second / first);
    } else if ("%".equals(input)) { // %
      stack.push(second % first);
    }
  }

  /**
   * Function that validates the input and executes the corresponding feature.
   * @param input The input from the user.
   *              Input must be one of the following: "+", "-", "*", "/", "%",
   *              "?", ".", "!", "[0-9]+" (Integers).
   * @param stack The stack that holds the integer data.
   * @return 0 if the input is an operator, 1 if the input is invalid, 2 if the input is a
   *         stub (feature executed, go to next loop or terminate program), 3 if the input added to the stack.
   */
  private static int validation(String input, LinkedStack<Integer> stack, int count) {
    if (isOperator(input)) { // Operators (+, -, *, /, %)
      return 0;
    } else if ("?".equals(input)) { // Feature executed, go to next loop
      System.out.println(stack);
      return 2;
    } else if (".".equals(input)) {
      if (!operandValid(count, 1)) {
        return 2;
      }
      // Feature executed, go to next loop
      System.out.println(stack.top());
      return 2;
    } else if ("!".equals(input)) {
      // Terminate program
      return 2;
    } else if (input.matches("[+-]?[0-9]+|0")) { // [+-]?[0-9]+ // [+-]?(?!0\d+)\d+
      // Feature executed, go to next loop
      stack.push(Integer.parseInt(input));
      return 3;
    }
    // Invalid input
    return 1;
  }

  /**
   * Checks if there are enough operands for the operation.
   * @param count The number of integers in the stack.
   *        Pre: count >= 0
   * @param type The type of operation, where 1 is to check for ".", 2 is to check for operators.
   *        Pre: type == 1 || type == 2
   * @return True if there are enough operands, false otherwise.
   */
  private static boolean operandValid(int count, int type) {
    if (type == 1 && count < 1) {
      System.out.println("ERROR: not enough operands");
      return false;
    } else if (type == 2 && count < 2) {
      System.out.println("ERROR: not enough operands");
      return false;
    }
    return true;
  }

  /**
   * Checks if the input is an operator.
   * @param input The input from the user that is assumed to be an operator.
   *        Pre: input contains +, -, *, /, or %
   * @return True if the input is an operator, false otherwise.
   */
  private static boolean isOperator(String input) {
    return "+".equals(input) || "-".equals(input) || "*".equals(input) || "/".equals(input) || "%".equals(input);
  }
}