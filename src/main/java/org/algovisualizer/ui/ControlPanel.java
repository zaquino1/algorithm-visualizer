package org.algovisualizer.ui;

import org.algovisualizer.controller.AlgorithmController;
import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.AlgorithmType;
import org.algovisualizer.observer.AlgorithmObserver;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel implements AlgorithmObserver {
    private final AlgorithmController controller;
    private final String category;
    private JComboBox<AlgorithmType> algorithmSelector;
    private JSlider speedSlider;
    private JTextArea customDataArea;
    private JButton playButton, pauseButton, stepForwardButton, stepBackButton, resetButton, generateButton, customDataButton;

    public ControlPanel(AlgorithmController controller, AlgorithmType[] availableAlgorithms, String category) {
        this.controller = controller;
        this.category = category;
        initializeUI(availableAlgorithms);
    }

    private void initializeUI(AlgorithmType[] availableAlgorithms) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.33;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createLeftPanel(availableAlgorithms), gbc);

        gbc.weightx = 0.34;
        gbc.gridx = 1;
        add(createCenterPanel(), gbc);

        gbc.weightx = 0.33;
        gbc.gridx = 2;
        add(createRightPanel(), gbc);
    }

    private JPanel createLeftPanel(AlgorithmType[] availableAlgorithms) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Algorithm:"));
        algorithmSelector = new JComboBox<>(availableAlgorithms);
        algorithmSelector.addActionListener(e -> {
            AlgorithmType selectedType = (AlgorithmType) algorithmSelector.getSelectedItem();
            if (selectedType != null) {
                controller.setAlgorithm(selectedType);
            }
        });
        panel.add(algorithmSelector);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepBackButton = new JButton("<<");
        stepBackButton.setToolTipText("Step Backward");
        stepBackButton.addActionListener(e -> controller.stepBack());

        playButton = new JButton("Play");
        playButton.addActionListener(e -> controller.play());

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> controller.pause());

        stepForwardButton = new JButton(">>");
        stepForwardButton.setToolTipText("Step Forward");
        stepForwardButton.addActionListener(e -> controller.stepForward());

        resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> controller.reset());

        panel.add(stepBackButton);
        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(stepForwardButton);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(resetButton);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        customDataArea = new JTextArea(2, 20);
        setCustomDataPlaceholder();
        panel.add(new JScrollPane(customDataArea));
        customDataButton = new JButton("Use Custom");
        customDataButton.addActionListener(e -> controller.setCustomData(customDataArea.getText()));
        panel.add(customDataButton);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> controller.generateRandomData());
        panel.add(generateButton);

        panel.add(new JLabel("Speed:"));
        speedSlider = new JSlider(1, 100, 50);
        speedSlider.addChangeListener(e -> controller.setSpeed(speedSlider.getValue()));
        panel.add(speedSlider);
        return panel;
    }

    private void setCustomDataPlaceholder() {
        switch (category) {
            case "Sorting":
            case "Searching":
                customDataArea.setText("e.g., 4,2,7,1,5");
                break;
            case "Tree":
                customDataArea.setText("e.g., 50,30,70,20,40,60,80");
                break;
            case "Graph":
                customDataArea.setText("Custom graph input not yet supported.");
                customDataArea.setEditable(false);
                break;
            default:
                customDataArea.setText("");
        }
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        if (state.getAlgorithmType() != null && algorithmSelector.getSelectedItem() != state.getAlgorithmType()) {
            algorithmSelector.setSelectedItem(state.getAlgorithmType());
        }
        boolean isPlaying = state.isPlaying();
        playButton.setEnabled(!isPlaying);
        pauseButton.setEnabled(isPlaying);
        stepBackButton.setEnabled(!isPlaying);
        stepForwardButton.setEnabled(!isPlaying);
        resetButton.setEnabled(!isPlaying);
        algorithmSelector.setEnabled(!isPlaying);
        customDataButton.setEnabled(!isPlaying);
        generateButton.setEnabled(!isPlaying);
    }
}