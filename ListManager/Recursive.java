package ListManager;
public class Recursive {

    public static int sumDigits(int n) {
        if (n == 0) {
            return 0;
        }
        return (n % 10) + sumDigits(n / 10);
    }

    public static int power(int base, int exp) {
        if (exp == 0) {
            return 1;
        }
        return base * power(base, exp - 1);
    }

    public static int countChar(String str, char target) {
        if (str.isEmpty()) {
            return 0;
        }
        int match = (str.charAt(0) == target) ? 1 : 0;
        return match + countChar(str.substring(1), target);
    }

    public static void printBinary(int n) {
        if (n == 0) {
            return;
        }
        printBinary(n / 2);
        System.out.print(n % 2);
    }

    public static void main(String[] args) {
        System.out.println("sumDigits(4096) = " + sumDigits(4096));
        System.out.println("power(2, 10) = " + power(2, 10));
        System.out.println("countChar(\"banana\", 'a') = " + countChar("banana", 'a'));
        
        System.out.print("printBinary(10) = ");
        printBinary(10);
        System.out.println();
    }
}