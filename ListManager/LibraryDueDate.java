package ListManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class LibraryDueDate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusDays(21);

        DayOfWeek dayOfWeek = dueDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            dueDate = dueDate.plusDays(2);
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            dueDate = dueDate.plusDays(1);
        }

        long daysUntilDue = ChronoUnit.DAYS.between(checkoutDate, dueDate);
        System.out.println("Checkout Date: " + checkoutDate.format(formatter));
        System.out.println("Due Date: " + dueDate.format(formatter) + " (Due in " + daysUntilDue + " days)");

        System.out.print("\nEnter actual return date (YYYY-MM-DD): ");
        String returnInput = scanner.nextLine().trim();
        LocalDate returnDate = LocalDate.parse(returnInput, formatter);

        double lateFee = 0.0;
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            lateFee = daysLate * 0.25;
        }

        System.out.printf("Late Fee: $%.2f%n", lateFee);

        scanner.close();
    }
}