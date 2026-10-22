package ListManager;
 import java.util.Scanner;
    public class LoginChecker {
    public static void main(String[] args) {
        String storedUser = "admin";
        String storedPass = "Secret123";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String inputUser = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPass = scanner.nextLine();

        if (storedUser.equalsIgnoreCase(inputUser) && storedPass.equals(inputPass)) {
            System.out.println("Welcome");
        } else {
            System.out.println("Access Denied");
        }
        System.out.println("\n--- Alphabetical Comparison ---");
        System.out.print("Enter first name: ");
        String name1 = scanner.nextLine();

        System.out.print("Enter second name: ");
        String name2 = scanner.nextLine();

        int comparison = name1.compareTo(name2);
        if (comparison < 0) {
            System.out.println(name1 + " comes first alphabetically.");
        } else if (comparison > 0) {
            System.out.println(name2 + " comes first alphabetically.");
        } else {
            System.out.println("Both names are the exact same.");
        }
        scanner.close();
    }
}

