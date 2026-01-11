package org.algovisualizer.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Integer, Node> nodes;
    private final List<Edge> edges;
    private final Map<Node, List<Edge>> adjacencyList;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node destination, int weight) {
        if (!nodes.containsValue(source) || !nodes.containsValue(destination)) {
            throw new IllegalArgumentException("Source or destination node not in graph.");
        }
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);
        adjacencyList.get(source).add(edge);
        // For undirected graphs, add the reverse edge as well
        // Edge reverseEdge = new Edge(destination, source, weight);
        // adjacencyList.get(destination).add(reverseEdge);
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public List<Node> getAllNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<Edge> getAllEdges() {
        return edges;
    }

    public List<Edge> getNeighbors(Node node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
}