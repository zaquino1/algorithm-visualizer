package org.algovisualizer.ui;

import org.algovisualizer.controller.AlgorithmController;
import org.algovisualizer.model.AlgorithmType;
import javax.swing.*;
import java.awt.*;

/**
 * Main application window containing all UI components.
 * Uses BorderLayout for flexible component arrangement.
 */
public class MainFrame extends JFrame {
    private final AlgorithmController controller;
    private final VisualizationPanel visualizationPanel;
    private final ControlPanel controlPanel;
    private final StatsPanel statsPanel;

    public MainFrame() {
        this.controller = new AlgorithmController();
        this.visualizationPanel = new VisualizationPanel();
        this.controlPanel = new ControlPanel(controller, visualizationPanel);
        this.statsPanel = new StatsPanel();

        controller.addObserver(visualizationPanel);
        controller.addObserver(statsPanel);

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Algorithm Visualizer - Interactive Learning Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(10, 10));

        // Add components
        add(createMenuBar(), BorderLayout.NORTH);
        add(visualizationPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.WEST);
        add(statsPanel, BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu algorithmMenu = new JMenu("Algorithms");
        JMenu sortingMenu = new JMenu("Sorting");
        JMenu searchingMenu = new JMenu("Searching");
        JMenu graphMenu = new JMenu("Graph");
        JMenu treeMenu = new JMenu("Tree");

        // Sorting algorithms
        addMenuItem(sortingMenu, "Bubble Sort", AlgorithmType.BUBBLE_SORT);
        addMenuItem(sortingMenu, "Selection Sort", AlgorithmType.SELECTION_SORT);
        addMenuItem(sortingMenu, "Insertion Sort", AlgorithmType.INSERTION_SORT);
        addMenuItem(sortingMenu, "Merge Sort", AlgorithmType.MERGE_SORT);
        addMenuItem(sortingMenu, "Quick Sort", AlgorithmType.QUICK_SORT);
        addMenuItem(sortingMenu, "Heap Sort", AlgorithmType.HEAP_SORT);

        // Searching algorithms
        addMenuItem(searchingMenu, "Linear Search", AlgorithmType.LINEAR_SEARCH);
        addMenuItem(searchingMenu, "Binary Search", AlgorithmType.BINARY_SEARCH);

        algorithmMenu.add(sortingMenu);
        algorithmMenu.add(searchingMenu);
        algorithmMenu.add(graphMenu);
        algorithmMenu.add(treeMenu);

        menuBar.add(algorithmMenu);
        menuBar.add(createHelpMenu());

        return menuBar;
    }

    private void addMenuItem(JMenu menu, String name, AlgorithmType type) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> controller.setAlgorithm(type));
        menu.add(item);
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        return helpMenu;
    }

    private void showAboutDialog() {
        String message = """
            Algorithm Visualizer v1.0
            
            An interactive tool for learning and understanding algorithms.
            
            Features:
            - Step-by-step visualization
            - Performance metrics
            - Multiple algorithm categories
            - Adjustable speed control
            """;
        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }
}