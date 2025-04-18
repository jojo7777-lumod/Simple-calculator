import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        Operations operations = new Operations();
        Display display = new Display();
        ErrorHandler errorHandler = new ErrorHandler();
        HistoryManager history = new HistoryManager();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean keepRunning = true;
            
            while (keepRunning) {
                System.out.println("\n==== Calculator Menu ====");
                System.out.println("1. Perform Calculation");
                System.out.println("2. Show History");
                System.out.println("3. Clear History");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                    continue;
                }
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1 -> {
                        double num1 = inputHandler.getNumber(scanner);
                        char operator = inputHandler.getOperator(scanner);
                        double num2 = inputHandler.getNumber(scanner);
                        
                        try {
                            double result;
                            switch (operator) {
                                case '+' -> result = Operations.add(num1, num2);
                                case '-' -> result = Operations.subtract(num1, num2);
                                case '*' -> result = Operations.multiply(num1, num2);
                                case '/' -> {
                                    if (num2 == 0) {
                                        throw new ArithmeticException("Cannot divide by zero.");
                                    }
                                    result = operations.divide(num1, num2);
                                }
                                default -> {
                                    System.out.println("Invalid operator. Please try again.");
                                    continue;
                                }
                            }
                            
                            display.showResult(num1, num2, operator, result);
                            history.addRecord(num1, num2, operator, result);
                        } catch (ArithmeticException e) {
                            errorHandler.handleError(e);
                        }
                    }
                    case 2 -> history.showHistory();
                    case 3 -> history.clearHistory();
                    case 4 -> {
                        System.out.println("Goodbye!");
                        keepRunning = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}

// Handles user input
class InputHandler {
    public double getNumber(Scanner scanner) {
        System.out.print("Enter a number: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    public char getOperator(Scanner scanner) {
        System.out.print("Enter an operator (+, -, *, /): ");
        String input;
        do {
            input = scanner.next();
            if (input.length() == 1 && "+-*/".contains(input)) {
                return input.charAt(0);
            }
            System.out.print("Invalid operator. Enter +, -, *, or /: ");
        } while (true);
    }
}

class Operations {
    public static double add(double a, double b) { return a + b; }
    public static double subtract(double a, double b) { return a - b; }
    public static double multiply(double a, double b) { return a * b; }
    public static double divide(double a, double b) { return a / b; }
}

class Display {
    public void showResult(double num1, double num2, char operator, double result) {
        System.out.println("Result: " + num1 + " " + operator + " " + num2 + " = " + result);
    }
}

class ErrorHandler {
    public void handleError(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}

// Mo ManGE SA CALCULATION HISTORY
class HistoryManager {
    private final List<String> history = new ArrayList<>();

    public void addRecord(double num1, double num2, char operator, double result) {
        history.add(num1 + " " + operator + " " + num2 + " = " + result);
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("History is empty.");
        } else {
            System.out.println("\n==== Calculation History ====");
            for (String record : history) {
                System.out.println(record);
            }
        }
    }

    public void clearHistory() {
        history.clear();
        System.out.println("History cleared.");
    }
}
