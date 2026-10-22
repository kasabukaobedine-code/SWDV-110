package ListManager;
import java.util.Scanner;

public class NameEmailParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine().trim();

        int firstSpace = fullName.indexOf(" ");
        int lastSpace = fullName.lastIndexOf(" ");

        String firstName = "";
        String lastName = "";

        if (firstSpace == -1) {
            firstName = fullName;
            lastName = "No last name provided";
        } else {
            firstName = fullName.substring(0, firstSpace);
            lastName = fullName.substring(lastSpace + 1);
        }

        System.out.println("First Name: " + firstName);
        System.out.println("Last Name:  " + lastName);

        String[] nameParts = fullName.split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(Character.toUpperCase(part.charAt(0))).append(".");
            }
        }
        System.out.println("Initials:   " + initials.toString());

        System.out.print("\nEnter email address: ");
        String email = scanner.nextLine().trim();

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        if (atIndex != -1 && dotIndex != -1 && dotIndex > atIndex) {
            String user = email.substring(0, atIndex);
            String domain = email.substring(atIndex + 1);

            System.out.println("User:   " + user);
            System.out.println("Domain: " + domain);
        } else {
            System.out.println("invalid");
        }

        scanner.close();
    }
}
