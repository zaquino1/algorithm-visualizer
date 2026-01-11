package org.algovisualizer.algorithms.graph;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.graph.Node;
import org.algovisualizer.model.graph.Edge;

import java.awt.Color;
import java.util.*;

public class Dijkstra implements Algorithm {

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        return Collections.emptyIterator();
    }

    @Override
    public Iterator<AlgorithmState> execute(Graph graph) {
        List<AlgorithmState> states = new ArrayList<>();
        Node startNode = graph.getNode(0);
        if (startNode == null) return Collections.emptyIterator();

        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> predecessors = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<Node> settledNodes = new HashSet<>();
        int step = 0;

        for (Node node : graph.getAllNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        pq.add(startNode);

        states.add(createState(graph, "Starting Dijkstra's from " + startNode.getLabel(), ++step, distances));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            if (settledNodes.contains(currentNode)) continue;

            settledNodes.add(currentNode);
            currentNode.setColor(Color.RED);
            states.add(createState(graph, "Visiting node " + currentNode.getLabel(), ++step, distances));

            for (Edge edge : graph.getNeighbors(currentNode)) {
                Node neighbor = edge.getDestination();
                if (!settledNodes.contains(neighbor)) {
                    int newDist = distances.get(currentNode) + edge.getWeight();
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        predecessors.put(neighbor, currentNode);
                        pq.add(neighbor);
                        neighbor.setColor(Color.ORANGE);
                        states.add(createState(graph, "Updating distance for " + neighbor.getLabel(), ++step, distances));
                    }
                }
            }
            currentNode.setColor(Color.GREEN);
        }

        // Highlight the shortest path
        // This part can be improved to show the path to a specific destination
        states.add(createState(graph, "Dijkstra's Complete", ++step, distances));
        return states.iterator();
    }

    private AlgorithmState createState(Graph graph, String description, int step, Map<Node, Integer> distances) {
        AlgorithmState state = new AlgorithmState();
        state.setGraph(copyGraph(graph));
        state.setDescription(description + " | Distances: " + formatDistances(distances));
        state.setStepCount(step);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    private String formatDistances(Map<Node, Integer> distances) {
        StringBuilder sb = new StringBuilder();
        distances.forEach((node, dist) -> {
            sb.append(node.getLabel()).append(": ").append(dist == Integer.MAX_VALUE ? "∞" : dist).append(" ");
        });
        return sb.toString();
    }

    private Graph copyGraph(Graph original) {
        Graph copy = new Graph();
        Map<Integer, Node> newNodes = new HashMap<>();
        original.getAllNodes().forEach(oldNode -> {
            Node newNode = new Node(oldNode.getId(), oldNode.getLabel(), oldNode.getX(), oldNode.getY());
            newNode.setColor(oldNode.getColor());
            newNodes.put(newNode.getId(), newNode);
            copy.addNode(newNode);
        });
        original.getAllEdges().forEach(oldEdge -> {
            Node newSource = newNodes.get(oldEdge.getSource().getId());
            Node newDestination = newNodes.get(oldEdge.getDestination().getId());
            copy.addEdge(newSource, newDestination, oldEdge.getWeight());
        });
        return copy;
    }

    @Override
    public String getTimeComplexity() {
        return "O(E log V)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(V)";
    }
}