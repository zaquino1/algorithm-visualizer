package org.algovisualizer.model.tree;

import java.awt.Color;

public class TreeNode {
    private final int value;
    private TreeNode left;
    private TreeNode right;
    private int x, y; // Position for visualization
    private Color color;

    public TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.color = Color.LIGHT_GRAY; // Default color
    }

    // Getters
    public int getValue() { return value; }
    public TreeNode getLeft() { return left; }
    public TreeNode getRight() { return right; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }

    // Setters
    public void setLeft(TreeNode left) { this.left = left; }
    public void setRight(TreeNode right) { this.right = right; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setColor(Color color) { this.color = color; }
}