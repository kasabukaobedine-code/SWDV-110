package ListManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BirthdayExplorer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("Enter your birthday (yyyy-mm-dd): ");
        String input = scanner.nextLine().trim();
        LocalDate birthday = LocalDate.parse(input, formatter);

        System.out.println("Day of the week you were born: " + birthday.getDayOfWeek());

        int dayOfYear = birthday.getDayOfYear();
        System.out.println("Day of the year: " + dayOfYear);

        boolean isLeap = birthday.isLeapYear();
        System.out.println("Was that year a leap year? " + isLeap);

        int currentYear = LocalDate.now().getYear();
        LocalDate thisYearsBirthday = birthday.withYear(currentYear);
        LocalDate today = LocalDate.now();

        boolean hasPassed = thisYearsBirthday.isBefore(today);
        System.out.println("Has this year's birthday already passed? " + hasPassed);

        System.out.println("\n--- Day of the week for the next 5 years ---");
        for (int i = 0; i < 5; i++) {
            LocalDate futureBirthday = birthday.withYear(currentYear + i);
            System.out.println(futureBirthday.getYear() + ": " + futureBirthday.getDayOfWeek());
        }

        scanner.close();
    }
}