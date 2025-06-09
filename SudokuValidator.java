/*
 * Problem 1: Sudoku Validator With Custom Zones
Validate a 9x9 Sudoku board. In addition to standard validation, check custom zones 
(each with 9 unique cells) for digits 1–9 without repetition.
 */
import java.util.*;

public class SudokuValidator {
    public static boolean validate(int[][] board, List<List<int[]>> zones) {
        return areRowsValid(board) && areColsValid(board) && areZonesValid(board, zones);
    }

    // Checks if all rows contain unique digits 1-9
    private static boolean areRowsValid(int[][] board) {
        for (int row = 0; row < 9; row++) {
            boolean[] seen = new boolean[10];
            for (int col = 0; col < 9; col++) {
                int val = board[row][col];
                if (val < 1 || val > 9 || seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    // Checks if all columns contain unique digits 1-9
    private static boolean areColsValid(int[][] board) {
        for (int col = 0; col < 9; col++) {
            boolean[] seen = new boolean[10];
            for (int row = 0; row < 9; row++) {
                int val = board[row][col];
                if (val < 1 || val > 9 || seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    // Checks if all custom zones contain unique digits 1-9
    private static boolean areZonesValid(int[][] board, List<List<int[]>> zones) {
        for (List<int[]> zone : zones) {
            boolean[] seen = new boolean[10];
            for (int[] cell : zone) {
                int row = cell[0], col = cell[1];
                int val = board[row][col];
                if (val < 1 || val > 9 || seen[val]) return false;
                seen[val] = true;
            }
        }
        return true;
    }

    // Example usage and test cases
    public static void main(String[] args) {
        // Example valid Sudoku board
        int[][] board = {
            {8,2,7,1,5,4,3,9,6},
            {9,6,5,3,2,7,1,4,8},
            {3,4,1,6,8,9,7,5,2},
            {5,9,3,4,6,8,2,7,1},
            {4,7,2,5,1,3,6,8,9},
            {6,1,8,9,7,2,4,3,5},
            {7,8,6,2,3,5,9,1,4},
            {1,5,4,7,9,6,8,2,3},
            {2,3,9,8,4,1,5,6,7}
        };

        // Define custom zones (standard 3x3 blocks for demonstration)
        List<List<int[]>> zones = new ArrayList<>();
        for (int block = 0; block < 9; block++) {
            List<int[]> zone = new ArrayList<>();
            int startRow = (block / 3) * 3;
            int startCol = (block % 3) * 3;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    zone.add(new int[]{startRow + i, startCol + j});
            zones.add(zone);
        }

        // Test 1: Valid board
        System.out.println("Test 1 (Valid): " + validate(board, zones)); // Output: true

        // Test 2: Duplicate in a row
        board[0][0] = 9;
        System.out.println("Test 2 (Row duplicate): " + validate(board, zones)); // Output: false

        // Test 3: Duplicate in a custom zone
        board[0][0] = 8; // restore
        board[1][1] = 8; // duplicate 8 in top-left zone
        System.out.println("Test 3 (Zone duplicate): " + validate(board, zones)); // Output: false

        // Test 4: Invalid value
        board[1][1] = 0;
        System.out.println("Test 4 (Invalid value): " + validate(board, zones)); // Output: false
    }
}

/*
Sample Output:
Test 1 (Valid): true
Test 2 (Row duplicate): false
Test 3 (Zone duplicate): false
Test 4 (Invalid value): false
*/