package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.observer.AlgorithmObserver;
import javax.swing.*;
import java.awt.*;

/**
 * A panel for displaying algorithm statistics.
 * Note: This panel is currently not in use as stats are displayed in the InfoPanel.
 * It is kept for potential future use and to prevent compilation errors.
 */
public class StatsPanel extends JPanel implements AlgorithmObserver {
    private JLabel comparisonsLabel;
    private JLabel swapsLabel;
    private JLabel timeComplexityLabel;
    private JLabel executionTimeLabel;

    public StatsPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridLayout(1, 4, 10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(0, 60));

        comparisonsLabel = createStatLabel("Comparisons: 0");
        swapsLabel = createStatLabel("Swaps: 0");
        timeComplexityLabel = createStatLabel("Time: N/A");
        executionTimeLabel = createStatLabel("Elapsed: 0ms");

        add(comparisonsLabel);
        add(swapsLabel);
        add(timeComplexityLabel);
        add(executionTimeLabel);
    }

    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        comparisonsLabel.setText("Comparisons: " + state.getComparisons());
        swapsLabel.setText("Swaps: " + state.getSwaps());
        timeComplexityLabel.setText("Time: " + state.getTimeComplexity());
        executionTimeLabel.setText(String.format("Elapsed: %dms", state.getElapsedTime()));
    }
}