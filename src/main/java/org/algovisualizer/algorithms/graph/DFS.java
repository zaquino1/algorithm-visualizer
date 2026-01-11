package org.algovisualizer.algorithms.graph;

import org.algovisualizer.algorithms.Algorithm;
import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.graph.Node;
import org.algovisualizer.model.graph.Edge;

import java.awt.Color;
import java.util.*;

public class DFS implements Algorithm {

    private List<AlgorithmState> states;
    private Set<Node> visited;
    private int step;

    @Override
    public Iterator<AlgorithmState> execute(int[] array) {
        return Collections.emptyIterator();
    }

    @Override
    public Iterator<AlgorithmState> execute(Graph graph) {
        states = new ArrayList<>();
        visited = new HashSet<>();
        step = 0;

        Node startNode = graph.getNode(0);
        if (startNode == null) return Collections.emptyIterator();

        states.add(createState(graph, "Starting DFS from node " + startNode.getLabel(), ++step));
        dfsRecursive(graph, startNode);
        states.add(createState(graph, "DFS Complete", ++step));

        return states.iterator();
    }

    private void dfsRecursive(Graph graph, Node currentNode) {
        visited.add(currentNode);
        currentNode.setColor(Color.RED); // Visiting color
        states.add(createState(graph, "Visiting node " + currentNode.getLabel(), ++step));

        for (Edge edge : graph.getNeighbors(currentNode)) {
            Node neighbor = edge.getDestination();
            if (!visited.contains(neighbor)) {
                dfsRecursive(graph, neighbor);
            }
        }

        currentNode.setColor(Color.GREEN); // Finished color
        states.add(createState(graph, "Finished with node " + currentNode.getLabel(), ++step));
    }

    private AlgorithmState createState(Graph graph, String description, int step) {
        AlgorithmState state = new AlgorithmState();
        state.setGraph(copyGraph(graph));
        state.setDescription(description);
        state.setStepCount(step);
        state.setTimeComplexity(getTimeComplexity());
        return state;
    }

    private Graph copyGraph(Graph original) {
        Graph copy = new Graph();
        Map<Integer, Node> newNodes = new HashMap<>();
        
        for (Node oldNode : original.getAllNodes()) {
            Node newNode = new Node(oldNode.getId(), oldNode.getLabel(), oldNode.getX(), oldNode.getY());
            newNode.setColor(oldNode.getColor());
            newNodes.put(newNode.getId(), newNode);
            copy.addNode(newNode);
        }

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