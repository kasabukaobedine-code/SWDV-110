import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Calculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        try {
            System.out.print("Enter the first number: ");
            double num1 = Double.parseDouble(input.nextLine());

            System.out.print("Enter the second number: ");
            double num2 = Double.parseDouble(input.nextLine());

            ArrayList<Double> results = new ArrayList<>();

            results.add(num1 + num2);
            results.add(num1 - num2);
            results.add(num1 * num2);

            if (num2 != 0) {
                results.add(num1 / num2);
            } else {
                System.out.println("Cannot divide by zero.");
                results.add(Double.NaN);
            }

            System.out.println("\nResults:");
            String[] operations = {
                "Addition", "Subtraction",
                "Multiplication", "Division"
            };

            for (int i = 0; i < results.size(); i++) {
                System.out.println(
                    operations[i] + ": " + results.get(i)
                );
            }

            try (FileWriter writer =
                    new FileWriter("transactions.txt", true)) {
                for (int i = 0; i < results.size(); i++) {
                    writer.write(
                        operations[i] + ": " + results.get(i)
                        + System.lineSeparator()
                    );
                }
                writer.write(System.lineSeparator());
            }

            System.out.println(
                "Transactions saved to transactions.txt"
            );

        } catch (NumberFormatException e) {
            System.out.println(
                "Invalid input. Please enter valid numbers."
            );
        } catch (IOException e) {
            System.out.println(
                "Error saving transactions: " + e.getMessage()
            );
        } finally {
            input.close();
        }
    }
}