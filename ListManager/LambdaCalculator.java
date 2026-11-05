package ListManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LambdaCalculator {

    public interface MathOperation {
        double calculate(double a, double b);
    }

    public static void main(String[] args) {
        Map<String, MathOperation> operations = new HashMap<>();
        
        operations.put("+", (a, b) -> a + b);
        operations.put("-", (a, b) -> a - b);
        operations.put("*", (a, b) -> a * b);
        operations.put("/", (a, b) -> a / b);
        operations.put("%", (a, b) -> a % b);

        operations.put("^", (a, b) -> Math.pow(a, b));
        operations.put("max", (a, b) -> Math.max(a, b));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression (e.g. 12 * 4): ");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        
        if (parts.length == 3) {
            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);

            MathOperation op = operations.get(operator);

            if (op != null) {
                double result = op.calculate(num1, num2);
                System.out.println("Result: " + result);
            } else {
                System.out.println("Unknown operator");
            }
        } else {
            System.out.println("Invalid input format. Use: number operator number");
        }
        
        scanner.close();
    }
}