package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.graph.Edge;
import org.algovisualizer.model.graph.Graph;
import org.algovisualizer.model.graph.Node;
import org.algovisualizer.observer.AlgorithmObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class GraphVisualizerPanel extends JPanel implements AlgorithmObserver {
    private Graph graph;
    private Node draggedNode = null;
    private int dragOffsetX, dragOffsetY;

    public GraphVisualizerPanel() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (graph == null) return;
                for (Node node : graph.getAllNodes()) {
                    if (isWithinNode(e.getPoint(), node)) {
                        draggedNode = node;
                        dragOffsetX = e.getX() - node.getX();
                        dragOffsetY = e.getY() - node.getY();
                        break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedNode != null) {
                    draggedNode.setPosition(e.getX() - dragOffsetX, e.getY() - dragOffsetY);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedNode = null;
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private boolean isWithinNode(Point p, Node node) {
        return p.distance(node.getX(), node.getY()) <= 15;
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        if (state.getGraph() != null) {
            this.graph = state.getGraph();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (graph == null) return;

        g2d.setStroke(new BasicStroke(2));
        for (Edge edge : graph.getAllEdges()) {
            g2d.setColor(edge.getColor());
            Node source = edge.getSource();
            Node dest = edge.getDestination();
            g2d.draw(new Line2D.Float(source.getX(), source.getY(), dest.getX(), dest.getY()));
            
            String weight = String.valueOf(edge.getWeight());
            int midX = (source.getX() + dest.getX()) / 2;
            int midY = (source.getY() + dest.getY()) / 2;
            g2d.setColor(Color.BLUE);
            g2d.drawString(weight, midX, midY);
        }

        for (Node node : graph.getAllNodes()) {
            g2d.setColor(node.getColor());
            g2d.fillOval(node.getX() - 15, node.getY() - 15, 30, 30);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(node.getX() - 15, node.getY() - 15, 30, 30);
            g2d.drawString(node.getLabel(), node.getX() - 5, node.getY() + 5);
        }
    }
}