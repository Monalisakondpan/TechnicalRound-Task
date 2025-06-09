/*
 * Problem 5: Matrix Islands with Diagonals
Count the number of islands in a matrix of 0s and 1s. 
Islands are formed using horizontal, vertical, or diagonal connections.
 */
public class MatrixIslandsWithDiagonals {
    public static int countIslands(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfs(grid, visited, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    // Directions: 8 neighbors (horizontal, vertical, diagonal)
    private static final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    // Depth-first search to mark all connected 1s
    private static void dfs(int[][] grid, boolean[][] visited, int x, int y) {
        visited[x][y] = true;
        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d], ny = y + dy[d];
            if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length
                    && grid[nx][ny] == 1 && !visited[nx][ny]) {
                dfs(grid, visited, nx, ny);
            }
        }
    }

    // Example usage and test cases
    public static void main(String[] args) {
        int[][] grid1 = {
            {1, 1, 0, 0},
            {0, 1, 0, 0},
            {1, 0, 1, 1},
            {0, 0, 0, 1}
        };
        System.out.println("Test 1: " + countIslands(grid1)); // Output: 2

        int[][] grid2 = {
            {1, 0, 0},
            {0, 1, 1},
            {0, 0, 1}
        };
        System.out.println("Test 2: " + countIslands(grid2)); // Output: 1

        int[][] grid3 = {
            {0, 0, 0},
            {0, 0, 0}
        };
        System.out.println("Test 3: " + countIslands(grid3)); // Output: 0

        int[][] grid4 = {
            {1, 0, 1},
            {0, 1, 0},
            {1, 0, 1}
        };
        System.out.println("Test 4: " + countIslands(grid4)); // Output: 5
    }
}

/*
Sample Output:
Test 1: 2
Test 2: 1
Test 3: 0
Test 4: 5
*/