package ListManager;
import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;

public class TicketQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Deque<String> line = new ArrayDeque<>();
        Deque<String> stack = new ArrayDeque<>();
        
        String input = "";
        while (!input.equals("exit")) {
            System.out.println();
            System.out.println("1. New Ticket");
            System.out.println("2. Next (Serve)");
            System.out.println("3. Peek");
            System.out.println("4. Count");
            System.out.println("5. Undo");
            System.out.print("Enter option: ");
            input = sc.nextLine();
            
            if (input.equals("1")) {
                System.out.print("Enter ticket info: ");
                String t = sc.nextLine();
                line.offer(t); 
                System.out.println("Ticket added.");
                
            } else if (input.equals("2")) {
                String served = line.poll();
                if (served == null) {
                    System.out.println("Nothing to serve! Queue is empty.");
                } else {
                    System.out.println("Serving: " + served);
                    stack.push(served); 
                }
                
            } else if (input.equals("3")) {
                String nextUp = line.peek(); 
                if (nextUp == null) {
                    System.out.println("No tickets.");
                } else {
                    System.out.println("Next up: " + nextUp);
                }
                
            } else if (input.equals("4")) {
                System.out.println("Total: " + line.size());
                
            } else if (input.equals("5")) {
                if (stack.isEmpty()) {
                    System.out.println("Nothing to undo.");
                } else {
                    String old = stack.pop(); 
                    
                    ((ArrayDeque<String>) line).addFirst(old); 
                    
                    System.out.println("Undone: " + old);
                }
            }
        }
        sc.close();
    }
}
