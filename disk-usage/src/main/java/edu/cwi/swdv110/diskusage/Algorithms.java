package edu.cwi.swdv110.diskusage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Algorithms {

    public static final Comparator<FileInfo> BY_SIZE_DESC =
            Comparator.comparingLong(FileInfo::size).reversed().thenComparing(FileInfo::path);

    public static final Comparator<FileInfo> BY_PATH = Comparator.comparing(FileInfo::path);

    private Algorithms() { }

    public static <T> List<T> selectionSort(List<T> list, Comparator<T> cmp) {
        List<T> a = new ArrayList<>(list);
        int n = a.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (cmp.compare(a.get(j), a.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            T temp = a.get(i);
            a.set(i, a.get(minIdx));
            a.set(minIdx, temp);
        }
        return a;
    }

    public static <T> List<T> mergeSort(List<T> list, Comparator<T> cmp) {
        // Base case: a list of size 0 or 1 is already sorted
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }
        // Recursive case: split in half, sort each half, and merge
        int mid = list.size() / 2;
        List<T> left = mergeSort(list.subList(0, mid), cmp);
        List<T> right = mergeSort(list.subList(mid, list.size()), cmp);
        return merge(left, right, cmp);
    }

    private static <T> List<T> merge(List<T> left, List<T> right, Comparator<T> cmp) {
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;
        while (i < left.size() && j < right.size()) {
            if (cmp.compare(left.get(i), right.get(j)) <= 0) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }
        return merged;
    }

    public static int binarySearchByPath(List<FileInfo> sortedByPath, String path) {
        return binarySearch(sortedByPath, path, 0, sortedByPath.size() - 1);
    }

    private static int binarySearch(List<FileInfo> a, String path, int low, int high) {
        // Base case: search range is empty
        if (low > high) {
            return -1;
        }
        int mid = low + (high - low) / 2;
        int cmp = a.get(mid).path().compareTo(path);
        // Base case: exact path match found at mid
        if (cmp == 0) {
            return mid;
        } else if (cmp > 0) {
            // Recursive case: search the left half
            return binarySearch(a, path, low, mid - 1);
        } else {
            // Recursive case: search the right half
            return binarySearch(a, path, mid + 1, high);
        }
    }

    public static List<FileInfo> largest(List<FileInfo> files, int n) {
        List<FileInfo> sorted = mergeSort(files, BY_SIZE_DESC);
        if (n >= sorted.size()) {
            return sorted;
        }
        return new ArrayList<>(sorted.subList(0, n));
    }
}
