package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.observer.AlgorithmObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class VisualizationPanel extends JPanel implements AlgorithmObserver {
    private AlgorithmState currentState;

    // High-contrast colors for better visibility
    private Color normalColor = new Color(66, 135, 245);
    private static final Color COMPARING_COLOR = new Color(245, 188, 66);
    private static final Color SWAPPING_COLOR = new Color(245, 66, 66);
    private static final Color SORTED_COLOR = new Color(78, 245, 66);

    public VisualizationPanel() {
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        this.currentState = state;
        repaint();
    }

    public Color getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(Color color) {
        this.normalColor = color;
        repaint(); // Repaint to show the new color immediately
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (currentState == null || currentState.getArray() == null || currentState.getArray().length == 0) {
            drawWelcomeMessage(g2d);
            return;
        }

        drawArray(g2d);
    }

    private void drawWelcomeMessage(Graphics2D g2d) {
        g2d.setFont(new Font("SansSerif", Font.BOLD, 24));
        g2d.setColor(UIManager.getColor("Label.foreground"));
        String msg = "Select an algorithm and generate an array to begin";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(msg)) / 2;
        int y = getHeight() / 2;
        g2d.drawString(msg, x, y);
    }

    private void drawArray(Graphics2D g2d) {
        int[] array = currentState.getArray();
        int maxValue = Arrays.stream(array).max().orElse(1);
        int width = getWidth();
        int height = getHeight();
        double barWidth = (double) (width - 40) / array.length;
        double gap = barWidth * 0.1;
        double effectiveBarWidth = barWidth - gap;

        for (int i = 0; i < array.length; i++) {
            double barHeight = (double) array[i] / maxValue * (height - 60);
            if (barHeight < 1) barHeight = 1; // Ensure bar is at least 1px high
            double x = 20 + i * barWidth;
            double y = height - barHeight - 20;

            Color barColor = getBarColor(i);
            Paint paint = new GradientPaint((float)x, (float)y, barColor.brighter(),
                                            (float)x, (float)(y + barHeight), barColor.darker());
            g2d.setPaint(paint);

            RoundRectangle2D bar = new RoundRectangle2D.Double(x, y, effectiveBarWidth, barHeight, 15, 15);
            g2d.fill(bar);

            // Draw value with a contrasting shadow for readability
            if (effectiveBarWidth > 25) {
                String value = String.valueOf(array[i]);
                FontMetrics fm = g2d.getFontMetrics();
                float textX = (float) (x + (effectiveBarWidth - fm.stringWidth(value)) / 2);
                float textY = (float) (y + barHeight / 2) + fm.getAscent() / 2;

                if (barHeight < 30) {
                    textY = (float) y - 5;
                }
                
                g2d.setColor(Color.BLACK);
                g2d.drawString(value, textX + 1, textY + 1);

                g2d.setColor(Color.WHITE);
                g2d.drawString(value, textX, textY);
            }
        }
    }

    private Color getBarColor(int index) {
        if (currentState.getSortedIndices() != null && currentState.getSortedIndices().contains(index)) {
            return SORTED_COLOR;
        }
        if (currentState.getSwappingIndices() != null && currentState.getSwappingIndices().contains(index)) {
            return SWAPPING_COLOR;
        }
        if (currentState.getComparingIndices() != null && currentState.getComparingIndices().contains(index)) {
            return COMPARING_COLOR;
        }
        return normalColor;
    }
}