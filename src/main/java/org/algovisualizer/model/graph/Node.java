package org.algovisualizer.model.graph;

import java.awt.Color;

public class Node {
    private final int id;
    private final String label;
    private int x, y; // Position for visualization
    private Color color;

    public Node(int id, String label, int x, int y) {
        this.id = id;
        this.label = label;
        this.x = x;
        this.y = y;
        this.color = Color.LIGHT_GRAY; // Default color
    }

    // Getters
    public int getId() { return id; }
    public String getLabel() { return label; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }

    // Setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}