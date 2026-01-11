package org.algovisualizer.ui;

import org.algovisualizer.controller.AlgorithmController;
import org.algovisualizer.model.AlgorithmType;
import javax.swing.*;
import java.awt.*;

/**
 * Control panel for user interactions.
 * Provides controls for algorithm execution, speed adjustment, and array generation.
 */
public class ControlPanel extends JPanel {
    private final AlgorithmController controller;
    private final VisualizationPanel visualizationPanel;
    private JComboBox<AlgorithmType> algorithmSelector;
    private JSpinner arraySizeSpinner;
    private JSlider speedSlider;
    private JButton runButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton generateButton;
    private JTextField customArrayField;

    public ControlPanel(AlgorithmController controller, VisualizationPanel visualizationPanel) {
        this.controller = controller;
        this.visualizationPanel = visualizationPanel;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(300, 0));

        add(createAlgorithmSection());
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createArraySection());
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createControlSection());
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(createSpeedSection());
    }

    private JPanel createAlgorithmSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Algorithm Selection"));

        algorithmSelector = new JComboBox<>(AlgorithmType.values());
        algorithmSelector.addActionListener(e ->
                controller.setAlgorithm((AlgorithmType) algorithmSelector.getSelectedItem()));

        panel.add(algorithmSelector);
        return panel;
    }

    private JPanel createArraySection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Array Configuration"));

        // Array size
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sizePanel.add(new JLabel("Size:"));
        arraySizeSpinner = new JSpinner(new SpinnerNumberModel(20, 5, 100, 5));
        sizePanel.add(arraySizeSpinner);

        generateButton = new JButton("Generate Random Array");
        generateButton.addActionListener(e -> generateRandomArray());

        // Custom array input
        JPanel customPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customPanel.add(new JLabel("Custom:"));
        customArrayField = new JTextField(15);
        customArrayField.setToolTipText("Enter comma-separated values");
        customPanel.add(customArrayField);

        JButton customButton = new JButton("Use Custom");
        customButton.addActionListener(e -> useCustomArray());

        panel.add(sizePanel);
        panel.add(generateButton);
        panel.add(customPanel);
        panel.add(customButton);

        return panel;
    }

    private JPanel createControlSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Execution Controls"));

        runButton = new JButton("Run");
        runButton.addActionListener(e -> controller.run());

        stepButton = new JButton("Step");
        stepButton.addActionListener(e -> controller.step());

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> controller.reset());

        panel.add(runButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(stepButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(resetButton);

        return panel;
    }

    private JPanel createSpeedSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Animation Speed"));

        speedSlider = new JSlider(1, 100, 50);
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> controller.setSpeed(speedSlider.getValue()));

        panel.add(new JLabel("Slower ← → Faster"));
        panel.add(speedSlider);

        return panel;
    }

    private void generateRandomArray() {
        int size = (Integer) arraySizeSpinner.getValue();
        controller.generateRandomArray(size);
    }

    private void useCustomArray() {
        String input = customArrayField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter comma-separated values");
            return;
        }

        try {
            String[] parts = input.split(",");
            int[] array = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                array[i] = Integer.parseInt(parts[i].trim());
            }
            controller.setArray(array);
            customArrayField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Use comma-separated integers.");
        }
    }
}