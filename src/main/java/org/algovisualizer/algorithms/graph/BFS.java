package org.algovisualizer.algorithms.graph;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.graph.Node;
import org.algovisualizer.model.graph.Edge;

import java.awt.Color;
import java.util.*;

public class BFS implements Algorithm {

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        // This algorithm operates on graphs, so this method is not used.
        return Collections.emptyIterator();
    }

    @Override
    public Iterator<AlgorithmState> execute(Graph graph) {
        List<AlgorithmState> states = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        int step = 0;

        Node startNode = graph.getNode(0); // Assume starting from node 0
        if (startNode == null) return Collections.emptyIterator();

        queue.add(startNode);
        visited.add(startNode);
        startNode.setColor(Color.ORANGE);

        states.add(createState(graph, "Starting BFS from node " + startNode.getLabel(), ++step));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            currentNode.setColor(Color.RED);
            states.add(createState(graph, "Visiting node " + currentNode.getLabel(), ++step));

            for (Edge edge : graph.getNeighbors(currentNode)) {
                Node neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    neighbor.setColor(Color.ORANGE);
                    queue.add(neighbor);
                    states.add(createState(graph, "Adding " + neighbor.getLabel() + " to queue", ++step));
                }
            }
            currentNode.setColor(Color.GREEN);
            states.add(createState(graph, "Finished with node " + currentNode.getLabel(), ++step));
        }
        
        states.add(createState(graph, "BFS Complete", ++step));
        return states.iterator();
    }

    private AlgorithmState createState(Graph graph, String description, int step) {
        AlgorithmState state = new AlgorithmState();
        state.setGraph(copyGraph(graph)); // Use a copy to ensure state isolation
        state.setDescription(description);
        state.setStepCount(step);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    private Graph copyGraph(Graph original) {
        Graph copy = new Graph();
        Map<Integer, Node> newNodes = new HashMap<>();
        
        // Deep copy nodes
        for (Node oldNode : original.getAllNodes()) {
            Node newNode = new Node(oldNode.getId(), oldNode.getLabel(), oldNode.getX(), oldNode.getY());
            newNode.setColor(oldNode.getColor());
            newNodes.put(newNode.getId(), newNode);
            copy.addNode(newNode);
        }

        // Deep copy edges
        for (Edge oldEdge : original.getAllEdges()) {
            Node newSource = newNodes.get(oldEdge.getSource().getId());
            Node newDestination = newNodes.get(oldEdge.getDestination().getId());
            copy.addEdge(newSource, newDestination, oldEdge.getWeight());
        }
        return copy;
    }

    @Override
    public String getTimeComplexity() {
        return "O(V + E)";
    }

    @Override
    public String getSpaceComplexity() {
        return "O(V)";
    }
}