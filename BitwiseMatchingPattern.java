/*
 *  Problem 4: Bitwise Matching Pattern
Given an integer n, return the next larger integer with the same number of binary 1s as n.
 */
public class BitwiseMatchingPattern {

    public static int nextLargerWithSameOnes(int n) {
        if (n == 0) return -1;

        int c = n;
        int c0 = 0; // count of trailing zeros
        int c1 = 0; // count of ones right after trailing zeros

        // Count trailing zeros (c0)
        while (((c & 1) == 0) && (c != 0)) {
            c0++;
            c >>= 1;
        }

        // Count ones (c1)
        while ((c & 1) == 1) {
            c1++;
            c >>= 1;
        }

        // If n is like 111...1100...00, then there is no bigger number
        if (c0 + c1 == 31 || c0 + c1 == 0) return -1;

        // Position of rightmost non-trailing zero
        int p = c0 + c1;

        // Flip rightmost non-trailing zero
        n |= (1 << p);

        // Clear all bits to the right of p
        n &= ~((1 << p) - 1);

        // Insert (c1-1) ones on the right
        n |= (1 << (c1 - 1)) - 1;

        return n;
    }

    // Example usage and test cases
    public static void main(String[] args) {
        int[] testCases = {5, 6, 7, 8, 12, 0, 1, 15};
        for (int n : testCases) {
            int next = nextLargerWithSameOnes(n);
            System.out.printf("n = %d (%s) -> %d (%s)%n",
                n, Integer.toBinaryString(n),
                next, next == -1 ? "N/A" : Integer.toBinaryString(next));
        }
    }
}

/*
Sample Output:
n = 5 (101) -> 6 (110)
n = 6 (110) -> 9 (1001)
n = 7 (111) -> 11 (1011)
n = 8 (1000) -> 16 (10000)
n = 12 (1100) -> 17 (10001)
n = 0 (0) -> -1 (N/A)
n = 1 (1) -> 2 (10)
n = 15 (1111) -> 23 (10111)
*/