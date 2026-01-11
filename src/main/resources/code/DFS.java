public class DFS {
    public void dfs(Graph graph, Node start) {
        Set<Node> visited = new HashSet<>();
        dfsRecursive(start, visited, graph);
    }

    private void dfsRecursive(Node current, Set<Node> visited, Graph graph) {
        visited.add(current);
        // Process current node

        for (Edge edge : graph.getNeighbors(current)) {
            Node neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, graph);
            }
        }
    }
}