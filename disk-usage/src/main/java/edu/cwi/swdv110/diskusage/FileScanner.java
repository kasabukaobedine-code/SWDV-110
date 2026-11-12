package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public final class FileScanner {

    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB"};

    private FileScanner() { }

    static File[] sortedChildren(File dir) {
        File[] kids = dir.listFiles();
        if (kids == null) {
            return new File[0];
        }
        Arrays.sort(kids, Comparator.comparing(File::getName));
        return kids;
    }

    public static long totalSize(File f) {
        // Base case: a file's size is its length
        if (f.isFile()) {
            return f.length();
        }
        // Recursive case: sum the totalSize of each child in the folder
        long sum = 0;
        File[] children = sortedChildren(f);
        for (File child : children) {
            sum += totalSize(child);
        }
        return sum;
    }

    public static int countFiles(File f) {
        // Base case: a single file counts as 1
        if (f.isFile()) {
            return 1;
        }
        // Recursive case: sum the file counts of each child in the folder
        int count = 0;
        File[] children = sortedChildren(f);
        for (File child : children) {
            count += countFiles(child);
        }
        return count;
    }

    public static int maxDepth(File f) {
        // Base case: a file or empty folder has depth 0
        if (f.isFile()) {
            return 0;
        }
        File[] children = sortedChildren(f);
        if (children.length == 0) {
            return 0;
        }
        // Recursive case: 1 plus the deepest child folder
        int deepest = 0;
        for (File child : children) {
            int childDepth = maxDepth(child);
            if (childDepth > deepest) {
                deepest = childDepth;
            }
        }
        return 1 + deepest;
    }

    public static List<FileInfo> listAll(File root) {
        List<FileInfo> result = new ArrayList<>();
        if (root.isFile()) {
            result.add(new FileInfo(root.getName(), root.length()));
        } else if (root.isDirectory()) {
            collect(root, "", result);
        }
        return result;
    }

    private static void collect(File dir, String prefix, List<FileInfo> out) {
        // Base case: loop finishes naturally if dir has no children
        File[] children = sortedChildren(dir);
        for (File child : children) {
            if (child.isFile()) {
                out.add(new FileInfo(prefix + child.getName(), child.length()));
            } else if (child.isDirectory()) {
                // Recursive case: step into subfolder with updated path prefix
                collect(child, prefix + child.getName() + "/", out);
            }
        }
    }

    public static List<FileInfo> findByExtension(File root, String ext) {
        List<FileInfo> allFiles = listAll(root);
        List<FileInfo> matches = new ArrayList<>();
        String target = "." + ext.toLowerCase();
        
        for (FileInfo info : allFiles) {
            if (info.path().toLowerCase().endsWith(target)) {
                matches.add(info);
            }
        }
        return matches;
    }

    public static String humanSize(long bytes) {
        return humanSize(bytes, 0);
    }

    private static String humanSize(double value, int unit) {
        // Base case: value is under 1024 or we reached the last unit
        if (value < 1024 || unit >= UNITS.length - 1) {
            if (unit == 0) {
                return String.format(Locale.US, "%.0f %s", value, UNITS[unit]);
            }
            return String.format(Locale.US, "%.1f %s", value, UNITS[unit]);
        }
        // Recursive case: divide by 1024 and advance to the next unit
        return humanSize(value / 1024.0, unit + 1);
    }

    public static String tree(File root, int maxDepth) {
        StringBuilder sb = new StringBuilder();
        tree(root, 0, maxDepth, sb);
        return sb.toString();
    }

    private static void tree(File f, int depth, int maxDepth, StringBuilder sb) {
        String indent = "  ".repeat(depth);
        long size = totalSize(f);
        String formattedSize = humanSize(size);

        if (f.isDirectory()) {
            sb.append(indent).append(f.getName()).append("/ (").append(formattedSize).append(")").append(System.lineSeparator());
            // Base case: stop recursing once depth reaches maxDepth
            if (depth < maxDepth) {
                // Recursive case: print each child at depth + 1
                File[] children = sortedChildren(f);
                for (File child : children) {
                    tree(child, depth + 1, maxDepth, sb);
                }
            }
        } else {
            sb.append(indent).append(f.getName()).append(" (").append(formattedSize).append(")").append(System.lineSeparator());
        }
    }
}
