package ListManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesReport {

    public record Sale(String product, String region, int qty, double price) {}

    public static void main(String[] args) {
        List<Sale> sales = List.of(
            new Sale("Laptop", "North", 5, 1200.0),
            new Sale("Mouse", "South", 50, 25.0),
            new Sale("Keyboard", "East", 30, 45.0),
            new Sale("Monitor", "West", 10, 300.0),
            new Sale("Laptop", "South", 2, 1200.0),
            new Sale("Desk Chair", "North", 15, 150.0),
            new Sale("Mouse", "East", 40, 25.0),
            new Sale("Monitor", "North", 8, 300.0),
            new Sale("Desk Chair", "West", 3, 150.0),
            new Sale("Keyboard", "South", 12, 45.0)
        );

        double totalRevenue = sales.stream()
                .mapToDouble(s -> s.qty() * s.price())
                .sum();
        System.out.println("Total Revenue: $" + totalRevenue);

        System.out.println("\nProducts with revenue over $100:");
        sales.stream()
                .filter(s -> (s.qty() * s.price()) > 100.0)
                .map(Sale::product)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        Sale biggestSale = sales.stream()
                .max((s1, s2) -> Double.compare(s1.qty() * s1.price(), s2.qty() * s2.price()))
                .orElse(null);
        System.out.println("\nBiggest Sale: " + biggestSale);

        Map<String, Double> revenueByRegion = sales.stream()
                .collect(Collectors.groupingBy(
                        Sale::region,
                        Collectors.summingDouble(s -> s.qty() * s.price())
                ));
        
        System.out.println("\nRevenue per Region:");
        revenueByRegion.forEach((region, rev) -> System.out.println(region + ": $" + rev));
    }
}