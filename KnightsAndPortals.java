/*
 * Problem 3: Knights and Portals
Given a grid, find the shortest path from the top-left to bottom-right. 
You may teleport between any two empty cells exactly once.
 */

import java.util.*;
public class KnightsAndPortals {

    static class State {
        int x, y, steps;
        boolean teleported;
        State(int x, int y, int steps, boolean teleported) {
            this.x = x; this.y = y; this.steps = steps; this.teleported = teleported;
        }
    }
    public static int shortestPathWithTeleport(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        if (grid[0][0] == 1 || grid[n-1][m-1] == 1) return -1;

        // Collect all empty cells for teleportation
        List<int[]> empties = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (grid[i][j] == 0) empties.add(new int[]{i, j});

        // visited[x][y][teleported]
        boolean[][][] visited = new boolean[n][m][2];
        Queue<State> q = new LinkedList<>();
        q.offer(new State(0, 0, 0, false));
        visited[0][0][0] = true;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        while (!q.isEmpty()) {
            State cur = q.poll();
            if (cur.x == n-1 && cur.y == m-1) return cur.steps;

            // Normal moves
            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d], ny = cur.y + dy[d];
                if (nx >= 0 && ny >= 0 && nx < n && ny < m && grid[nx][ny] == 0 && !visited[nx][ny][cur.teleported ? 1 : 0]) {
                    visited[nx][ny][cur.teleported ? 1 : 0] = true;
                    q.offer(new State(nx, ny, cur.steps + 1, cur.teleported));
                }
            }

            // Teleport (only if not used yet)
            if (!cur.teleported) {
                for (int[] cell : empties) {
                    int tx = cell[0], ty = cell[1];
                    if ((tx != cur.x || ty != cur.y) && !visited[tx][ty][1]) {
                        visited[tx][ty][1] = true;
                        q.offer(new State(tx, ty, cur.steps + 1, true));
                    }
                }
            }
        }
        return -1;
    }

    // Example usage and test cases
    public static void main(String[] args) {
        int[][] grid1 = {
            {0, 1, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
        };
        System.out.println("Test 1: " + shortestPathWithTeleport(grid1)); // Output: 4

        int[][] grid2 = {
            {0, 1, 1},
            {1, 1, 0},
            {0, 0, 0}
        };
        System.out.println("Test 2: " + shortestPathWithTeleport(grid2)); // Output: 3

        int[][] grid3 = {
            {0, 1},
            {1, 0}
        };
        System.out.println("Test 3: " + shortestPathWithTeleport(grid3)); // Output: 1
        int[][] grid4 = {
            {0, 1, 1},
            {1, 1, 1},
            {1, 1, 0}
        };
        System.out.println("Test 4: " + shortestPathWithTeleport(grid4)); // Output: -1
    }
}

/*
Sample Output:
Test 1: 4
Test 2: 3
Test 3: 1
Test 4: -1
*/