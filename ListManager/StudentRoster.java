package ListManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentRoster {

    public record Student(String name, String major, double gpa) {}

    public static void main(String[] args) {
        List<Student> roster = new ArrayList<>();
        roster.add(new Student("Alice", "Computer Science", 3.8));
        roster.add(new Student("Bob", "Mathematics", 1.9));
        roster.add(new Student("Charlie", "Computer Science", 3.4));
        roster.add(new Student("Diana", "Physics", 3.9));
        roster.add(new Student("Ethan", "Mathematics", 2.2));
        roster.add(new Student("Fiona", "Biology", 1.7));
        roster.add(new Student("George", "Physics", 3.1));
        roster.add(new Student("Hannah", "Biology", 3.6));

        System.out.println("--- Sorted by Name ---");
        roster.stream()
              .sorted(Comparator.comparing(Student::name))
              .forEach(s -> System.out.println(s.name() + " | " + s.major() + " | " + s.gpa()));

        System.out.println("\n--- Sorted by Major, then GPA (High to Low) ---");
        roster.stream()
              .sorted(Comparator.comparing(Student::major)
                                .thenComparing(Comparator.comparingDouble(Student::gpa).reversed()))
              .forEach(s -> System.out.println(s.major() + " - " + s.name() + " (" + s.gpa() + ")"));

        roster.removeIf(s -> s.gpa() < 2.0);

        System.out.println("\n--- Remaining Students (Uppercase Names) ---");
        roster.forEach(s -> System.out.println(s.name().toUpperCase()));
    }
}
