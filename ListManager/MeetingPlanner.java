package ListManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Scanner;

public class MeetingPlanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a", Locale.ENGLISH);

        ZonedDateTime boiseMeeting = null;

        while (boiseMeeting == null) {
            System.out.print("Enter meeting date and time in Boise (M/d/yyyy h:mm a): ");
            String input = scanner.nextLine().trim();

            try {
                LocalDateTime localInput = LocalDateTime.parse(input, formatter);
                boiseMeeting = ZonedDateTime.of(localInput, ZoneId.of("America/Boise"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please try again (e.g., 10/28/2026 2:30 PM).");
            }
        }

        ZonedDateTime nyMeeting = boiseMeeting.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime londonMeeting = boiseMeeting.withZoneSameInstant(ZoneId.of("Europe/London"));
        ZonedDateTime tokyoMeeting = boiseMeeting.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));

        System.out.println("\n--- Meeting Times Around the World ---");
        printCityTime("New York", nyMeeting);
        printCityTime("London", londonMeeting);
        printCityTime("Tokyo", tokyoMeeting);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Boise"));
        if (boiseMeeting.isAfter(now)) {
            long days = ChronoUnit.DAYS.between(now, boiseMeeting);
            long hours = ChronoUnit.HOURS.between(now, boiseMeeting) % 24;
            long minutes = ChronoUnit.MINUTES.between(now, boiseMeeting) % 60;

            System.out.println("\nCountdown to meeting: " + days + " days, " + hours + " hours, and " + minutes + " minutes.");
        } else {
            System.out.println("\nThis meeting time has already passed.");
        }

        scanner.close();
    }

    private static void printCityTime(String cityName, ZonedDateTime time) {
        DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
        int hour = time.getHour();
        boolean outsideBusinessHours = (hour < 9 || hour >= 17);

        System.out.print(cityName + ": " + time.format(outFormatter));
        if (outsideBusinessHours) {
            System.out.println(" [FLAGGED: Outside 9 AM - 5 PM]");
        } else {
            System.out.println(" (Within business hours)");
        }
    }
}
