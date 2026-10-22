package ListManager;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class WordFrequencyCounter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a paragraph: ");
        String input = sc.nextLine();
        String cleanedText = input.toLowerCase().replaceAll("[^a-z ]", "");
        String[] words = cleanedText.split("\\s+");

        Map<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (wordCounts.containsKey(word)) {
                    int count = wordCounts.get(word);
                    wordCounts.put(word, count + 1);
                } else {
                    wordCounts.put(word, 1);
                }
            }
        }

        System.out.println("\nWords alphabetically with counts:");
        Map<String, Integer> sortedCounts = (wordCounts);
        for (Map.Entry<String, Integer> entry : sortedCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        wordCounts.keySet();
        System.out.println("\nNumber of unique words: " );

        String mostFrequentWord = "";
        int maxCount = -1;

        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentWord = entry.getKey();
            }
        }
        System.out.println("Most frequent word: " + mostFrequentWord);
        
        sc.close();
    }
}
