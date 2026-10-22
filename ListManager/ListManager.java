package ListManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListManager {
    public static void main(String[] args) {
        List<String> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String command = "";

        System.out.println("=== To-Do List Manager ===");
        System.out.println("Commands: add, remove, list, done <number>, quit");

        while (true) {
            System.out.print("\nEnter command (add, remove, list, done <num>, quit): ");
            command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("quit")) {
                System.out.println("Goodbye!");
                break;
            }
            else if (command.equals("add")) {
                System.out.print("Enter the task to add: ");
                String newTask = scanner.nextLine();
                tasks.add(newTask);
                System.out.println("Task added successfully.");
            }
            else if (command.equals("list")) {
                System.out.println("\n--- Current Tasks ---");
                if (tasks.isEmpty()) {
                    System.out.println("Your task list is empty.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
            }

            else if (command.equals("remove")) {
                if (tasks.isEmpty()) {
                    System.out.println("No tasks to remove.");
                    continue;
                }
                System.out.print("Enter the task name or number to remove: ");
                String target = scanner.nextLine().trim();
                
                try {
                    int taskNum = Integer.parseInt(target);
                    if (taskNum > 0 && taskNum <= tasks.size()) {
                        String removed = tasks.remove(taskNum - 1);
                        System.out.println("Removed: " + removed);
                    } else {
                        System.out.println("Friendly note: That number doesn't exist on your list! Please try a valid task number.");
                    }
                } catch (NumberFormatException e) {
                    if (tasks.remove(target)) {
                        System.out.println("Removed task: " + target);
                    } else {
                        System.out.println("Task not found.");
                    }
                }
            }
            else if (command.startsWith("done")) {
                String[] parts = command.split("\\s+");
                if (parts.length < 2) {
                    System.out.println("Friendly note: Please specify the task number (e.g., 'done 2').");
                    continue;
                }

                try {
                    int userNum = Integer.parseInt(parts[1]);
                    int index = userNum - 1;

                    if (index >= 0 && index < tasks.size()) {
                        String completedTask = tasks.remove(index);
                        System.out.println("Completed and removed: " + completedTask);
                    } else {
                        System.out.println("Friendly note: Task #" + userNum + " is invalid. Please check your list numbers!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Friendly note: Please enter a valid number after 'done' (e.g., 'done 1').");
                }
            }

            else {
                System.out.println("Unknown command. Please use: add, remove, list, done <number>, or quit.");
            }
        }

        scanner.close();
    }
}