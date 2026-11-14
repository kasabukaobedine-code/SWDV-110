package ListManager;

public class BinarySearch {

    public static int binarySearch(int[] a, int target, int low, int high, int[] count) {
        count[0]++;
        
        if (low > high) {
            return -1;
        }
        
        int mid = low + (high - low) / 2;
        
        if (a[mid] == target) {
            return mid;
        }
        
        if (a[mid] < target) {
            return binarySearch(a, target, mid + 1, high, count);
        } else {
            return binarySearch(a, target, low, mid - 1, count);
        }
    }

    public static int linearSearch(int[] a, int target, int[] count) {
        for (int i = 0; i < a.length; i++) {
            count[0]++;
            if (a[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[1000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i * 2;
        }

        int first = nums[0];
        int middle = nums[nums.length / 2];
        int last = nums[nums.length - 1];
        int missing = 9999;

        int[] targets = {first, middle, last, missing};
        String[] labels = {"First", "Middle", "Last", "Missing"};

        System.out.println("--- Comparing Binary Search vs Linear Search ---");

        for (int i = 0; i < targets.length; i++) {
            int t = targets[i];

            int[] binCount = {0};
              binarySearch(nums, t, 0, nums.length - 1, binCount);

            int[] linCount = {0};
              linearSearch(nums, t, linCount);

            System.out.println("\nTesting: " + labels[i] + " (target: " + t + ")");
            System.out.println("Binary Search checks: " + binCount[0]);
            System.out.println("Linear Search checks: " + linCount[0]);
        }
    }
}
