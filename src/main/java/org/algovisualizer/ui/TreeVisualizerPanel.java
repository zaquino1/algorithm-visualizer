package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.tree.Tree;
import org.algovisualizer.model.tree.TreeNode;
import org.algovisualizer.observer.AlgorithmObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class TreeVisualizerPanel extends JPanel implements AlgorithmObserver {
    private Tree tree;

    public TreeVisualizerPanel() {}

    @Override
    public void onStateUpdate(AlgorithmState state) {
        if (state.getTree() != null) {
            this.tree = state.getTree();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (tree == null || tree.getRoot() == null) return;

        drawTree(g2d, tree.getRoot());
    }

    private void drawTree(Graphics2D g2d, TreeNode node) {
        if (node == null) return;

        // Draw connections
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GRAY);
        if (node.getLeft() != null) {
            g2d.draw(new Line2D.Float(node.getX(), node.getY(), node.getLeft().getX(), node.getLeft().getY()));
        }
        if (node.getRight() != null) {
            g2d.draw(new Line2D.Float(node.getX(), node.getY(), node.getRight().getX(), node.getRight().getY()));
        }

        // Draw node
        g2d.setColor(node.getColor());
        g2d.fillOval(node.getX() - 20, node.getY() - 20, 40, 40);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(node.getX() - 20, node.getY() - 20, 40, 40);
        g2d.drawString(String.valueOf(node.getValue()), node.getX() - 5, node.getY() + 5);

        drawTree(g2d, node.getLeft());
        drawTree(g2d, node.getRight());
    }
}