import java.util.*;

public class analysis3 {

    public static void main(String[] args) {
        int numVertices = 4;
        int[][] edges = {{1, 3}, {1, 4}, {2, 1}, {2, 3}, {3, 4}, {4, 1}, {4, 2}};

        // DFS
        System.out.println("DFS Traversal:");
        dfs(numVertices, edges, 1, true);
        System.out.println();

        // BFS
        System.out.println("BFS Traversal:");
        bfs(numVertices, edges, 1, true);
        System.out.println();

        // Cycle Detection
        List<List<Integer>> cycles = new ArrayList<>();
        if (hasCycle(numVertices, edges, cycles)) {
            System.out.println("Graph has cycles.");
            System.out.println("Cycles:");
            for (List<Integer> cycle : cycles) {
                System.out.println(cycle);
            }
        } else {
            System.out.println("Graph does not have cycles.");
        }
        
        // Bipartiteness Test
        if (isBipartite(numVertices, edges)) {
            System.out.println("Graph is bipartite.");
        } else {
            System.out.println("Graph is not bipartite.");
        }
    }
    

    // Depth-First Search
    private static void dfs(int numVertices, int[][] edges, int startNode, boolean printLeftToRight) {
        List<Integer> stack = new ArrayList<>();
        boolean[] visited = new boolean[numVertices + 1];

        stack.add(startNode);

        while (!stack.isEmpty()) {
            int currentNode = stack.remove(stack.size() - 1);

            if (!visited[currentNode]) {
                visited[currentNode] = true;
                System.out.print(currentNode + " ");

                List<Integer> neighbors = getNeighbors(edges, currentNode);
                if (neighbors != null) {
                    if (printLeftToRight) {
                        Collections.sort(neighbors);
                    } else {
                        Collections.sort(neighbors, Collections.reverseOrder());
                    }

                    stack.addAll(neighbors);
                }
            }
        }
    }

    // Breadth-First Search
    private static void bfs(int numVertices, int[][] edges, int startNode, boolean printLeftToRight) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");

            List<Integer> neighbors = getNeighbors(edges, currentNode);
            if (neighbors != null) {
                if (printLeftToRight) {
                    Collections.sort(neighbors);
                } else {
                    Collections.sort(neighbors, Collections.reverseOrder());
                }

                for (int neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }
        System.out.println();
    }

    // Cycle Detection
    private static boolean hasCycle(int numVertices, int[][] edges, List<List<Integer>> cycles) {
        boolean[] visited = new boolean[numVertices + 1];
        boolean[] recursionStack = new boolean[numVertices + 1];

        for (int node = 1; node <= numVertices; node++) {
            if (!visited[node] && hasCycleHelper(node, edges, visited, recursionStack, cycles)) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasCycleHelper(int currentNode, int[][] edges, boolean[] visited, boolean[] recursionStack, List<List<Integer>> cycles) {
        if (!visited[currentNode]) {
            visited[currentNode] = true;
            recursionStack[currentNode] = true;

            List<Integer> neighbors = getNeighbors(edges, currentNode);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    if (!visited[neighbor] && hasCycleHelper(neighbor, edges, visited, recursionStack, cycles)) {
                        return true;
                    } else if (recursionStack[neighbor]) {
                        addCycle(currentNode, neighbor, recursionStack, cycles);
                        return true;
                    }
                }
            }
        }

        recursionStack[currentNode] = false;
        return false;
    }
    
    private static void addCycle(int startNode, int endNode, boolean[] recursionStack, List<List<Integer>> cycles) {
        List<Integer> cycle = new ArrayList<>();
        for (int node = 1; node < recursionStack.length; node++) {
            if (recursionStack[node]) {
                cycle.add(node);
                if (node == endNode) {
                    break;
                }
            }
        }
        cycles.add(cycle);
    }

    private static List<Integer> getNeighbors(int[][] edges, int node) {
        List<Integer> neighbors = new ArrayList<>();
        for (int[] edge : edges) {
            if (edge[0] == node) {
                neighbors.add(edge[1]);
            }
        }
        return neighbors.isEmpty() ? null : neighbors;
    }
    
    // Bipartiteness Test
    private static boolean isBipartite(int numVertices, int[][] edges) {
        int[] colors = new int[numVertices + 1];
        Arrays.fill(colors, -1);

        for (int node = 1; node <= numVertices; node++) {
            if (colors[node] == -1 && !bfsIsBipartite(node, edges, colors)) {
                return false;
            }
        }

        return true;
    }

    private static boolean bfsIsBipartite(int startNode, int[][] edges, int[] colors) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        colors[startNode] = 0;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            List<Integer> neighbors = getNeighbors(edges, currentNode);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    if (colors[neighbor] == -1) {
                        colors[neighbor] = 1 - colors[currentNode];
                        queue.add(neighbor);
                    } else if (colors[neighbor] == colors[currentNode]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
