package org.algovisualizer.model.graph;

import java.awt.Color;

public class Edge {
    private final Node source;
    private final Node destination;
    private final int weight;
    private Color color;

    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.color = Color.GRAY; // Default color
    }

    // Getters
    public Node getSource() { return source; }
    public Node getDestination() { return destination; }
    public int getWeight() { return weight; }
    public Color getColor() { return color; }

    // Setter
    public void setColor(Color color) {
        this.color = color;
    }
}