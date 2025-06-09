/*
 *  Problem 2: Alien Dictionary
Given a sorted list of words from an alien language, 
determine the character order used in that language.
 */

import java.util.*;

public class AlienDictionary {

    public static String findOrder(String[] words) {
        // Step 1: Build the graph
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();

        // Initialize graph nodes
        for (String word : words) {
            for (char c : word.toCharArray()) {
                adj.putIfAbsent(c, new HashSet<>());
                indegree.putIfAbsent(c, 0);
            }
        }

        // Build edges
        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i], w2 = words[i + 1];
            int minLen = Math.min(w1.length(), w2.length());
            boolean found = false;
            for (int j = 0; j < minLen; j++) {
                char c1 = w1.charAt(j), c2 = w2.charAt(j);
                if (c1 != c2) {
                    if (!adj.get(c1).contains(c2)) {
                        adj.get(c1).add(c2);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    found = true;
                    break;
                }
            }
            // Edge case: prefix situation (invalid order)
            if (!found && w1.length() > w2.length()) return "";
        }

        // Step 2: Topological sort (Kahn's algorithm)
        Queue<Character> queue = new LinkedList<>();
        for (char c : indegree.keySet()) {
            if (indegree.get(c) == 0) queue.offer(c);
        }

        StringBuilder order = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            order.append(c);
            for (char nei : adj.get(c)) {
                indegree.put(nei, indegree.get(nei) - 1);
                if (indegree.get(nei) == 0) queue.offer(nei);
            }
        }

        // If not all characters are in the result, there was a cycle
        if (order.length() != indegree.size()) return "";
        return order.toString();
    }

    // Example usage and test cases
    public static void main(String[] args) {
        // Test 1: Simple case
        String[] words1 = {"wrt", "wrf", "er", "ett", "rftt"};
        System.out.println("Test 1: " + findOrder(words1)); // Output: "wertf"

        // Test 2: Single word
        String[] words2 = {"abc"};
        System.out.println("Test 2: " + findOrder(words2)); // Output: "abc"

        // Test 3: Invalid order (cycle)
        String[] words3 = {"z", "x", "z"};
        System.out.println("Test 3: " + findOrder(words3)); // Output: ""

        // Test 4: Prefix case (invalid)
        String[] words4 = {"abc", "ab"};
        System.out.println("Test 4: " + findOrder(words4)); // Output: ""
    }
}

/*
Sample Output:
Test 1: wertf
Test 2: abc
Test 3: 
Test 4: 
*/