package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Folder to scan (Enter for this project): ");
        String input = sc.hasNextLine() ? sc.nextLine().trim() : "";
        File root = Path.of(input.isEmpty() ? "." : input).toAbsolutePath().normalize().toFile();
        if (!root.isDirectory()) {
            System.out.println("Not a folder: " + root);
            return;
        }

        System.out.print(FileScanner.tree(root, 2));
        System.out.println();

        int fileCount = FileScanner.countFiles(root);
        long totalBytes = FileScanner.totalSize(root);
        int depth = FileScanner.maxDepth(root);
        System.out.println("Files: " + fileCount + "   Total: " + FileScanner.humanSize(totalBytes)
                + "   Deepest nesting: " + depth + " levels");
        System.out.println();

        List<FileInfo> allFiles = FileScanner.listAll(root);
        List<FileInfo> top5 = Algorithms.largest(allFiles, 5);
        System.out.println("Largest files:");
        for (FileInfo f : top5) {
            System.out.printf("%10s  %s%n", FileScanner.humanSize(f.size()), f.path());
        }
        System.out.println();

        Map<String, Integer> extCounts = new TreeMap<>();
        for (FileInfo f : allFiles) {
            String name = f.path();
            int slashIndex = name.lastIndexOf('/');
            if (slashIndex >= 0) {
                name = name.substring(slashIndex + 1);
            }
            int dotIndex = name.lastIndexOf('.');
            String ext = (dotIndex > 0 && dotIndex < name.length() - 1)
                    ? name.substring(dotIndex + 1).toLowerCase()
                    : "(none)";
            extCounts.put(ext, extCounts.getOrDefault(ext, 0) + 1);
        }
        System.out.println("Files by extension: " + extCounts);

        List<FileInfo> sortedByPath = Algorithms.mergeSort(allFiles, Algorithms.BY_PATH);

        while (true) {
            System.out.println();
            System.out.print("Find path (or 'race' / 'quit'): ");
            String query = sc.hasNextLine() ? sc.nextLine().trim() : "quit";

            if (query.equalsIgnoreCase("quit")) {
                break;
            } else if (query.equalsIgnoreCase("race")) {
                sortRace();
            } else {
                int idx = Algorithms.binarySearchByPath(sortedByPath, query);
                if (idx >= 0) {
                    FileInfo found = sortedByPath.get(idx);
                    System.out.println("Found at index " + idx + ": " + FileScanner.humanSize(found.size()));
                } else {
                    System.out.println("Not found: " + query);
                }
            }
        }
        sc.close();
    }

    static void sortRace() {
        Random rnd = new Random(110);
        System.out.printf("%8s %14s %12s%n", "n", "selection ms", "merge ms");
        for (int n = 1_000; n <= 16_000; n *= 2) {
            List<FileInfo> data = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                data.add(new FileInfo("file" + rnd.nextInt(1_000_000), rnd.nextInt(10_000_000)));
            }
            long t0 = System.nanoTime();
            Algorithms.selectionSort(data, Algorithms.BY_SIZE_DESC);
            long t1 = System.nanoTime();
            Algorithms.mergeSort(data, Algorithms.BY_SIZE_DESC);
            long t2 = System.nanoTime();
            System.out.printf("%8d %14.1f %12.1f%n", n, (t1 - t0) / 1e6, (t2 - t1) / 1e6);
        }
    }
}
