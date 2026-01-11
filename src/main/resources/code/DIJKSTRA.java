public class Dijkstra {
    public void dijkstra(Graph graph, Node start) {
        Map<Node, Integer> distances = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Node> settled = new HashSet<>();

        for (Node node : graph.getAllNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (settled.contains(current)) continue;
            
            settled.add(current);

            for (Edge edge : graph.getNeighbors(current)) {
                Node neighbor = edge.getDestination();
                if (!settled.contains(neighbor)) {
                    int newDist = distances.get(current) + edge.getWeight();
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        pq.add(neighbor);
                    }
                }
            }
        }
    }
}