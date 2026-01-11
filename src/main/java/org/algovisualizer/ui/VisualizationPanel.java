package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.observer.AlgorithmObserver;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Panel responsible for rendering the visual representation of algorithms.
 * Displays array elements as bars with color-coded states.
 */
public class VisualizationPanel extends JPanel implements AlgorithmObserver {
    private AlgorithmState currentState;
    private static final Color NORMAL_COLOR = new Color(70, 130, 180);
    private static final Color COMPARING_COLOR = new Color(255, 165, 0);
    private static final Color SWAPPING_COLOR = new Color(220, 20, 60);
    private static final Color SORTED_COLOR = new Color(60, 179, 113);

    public VisualizationPanel() {
        setPreferredSize(new Dimension(800, 500));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        this.currentState = state;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (currentState == null || currentState.getArray().length == 0) {
            drawWelcomeMessage(g2d);
            return;
        }

        drawArray(g2d);
        drawStepInfo(g2d);
    }

    private void drawWelcomeMessage(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.GRAY);
        String msg = "Select an algorithm and click 'Generate Array' to begin";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(msg)) / 2;
        int y = getHeight() / 2;
        g2d.drawString(msg, x, y);
    }

    private void drawArray(Graphics2D g2d) {
        int[] array = currentState.getArray();
        int maxValue = Arrays.stream(array).max().orElse(1);
        int width = getWidth();
        int height = getHeight() - 60;
        int barWidth = Math.max(2, (width - 40) / array.length);
        int gap = 2;

        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) ((double) array[i] / maxValue * height);
            int x = 20 + i * (barWidth + gap);
            int y = height - barHeight + 20;

            g2d.setColor(getBarColor(i));
            g2d.fillRect(x, y, barWidth, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, barWidth, barHeight);

            // Draw value if bars are wide enough
            if (barWidth > 20) {
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                String value = String.valueOf(array[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (barWidth - fm.stringWidth(value)) / 2;
                int textY = y - 5;
                g2d.drawString(value, textX, textY);
            }
        }
    }

    private Color getBarColor(int index) {
        if (currentState.getSortedIndices().contains(index)) {
            return SORTED_COLOR;
        }
        if (currentState.getSwappingIndices().contains(index)) {
            return SWAPPING_COLOR;
        }
        if (currentState.getComparingIndices().contains(index)) {
            return COMPARING_COLOR;
        }
        return NORMAL_COLOR;
    }

    private void drawStepInfo(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        String info = String.format("Step: %d | %s",
                currentState.getStepCount(),
                currentState.getDescription());
        g2d.drawString(info, 20, getHeight() - 20);
    }
}