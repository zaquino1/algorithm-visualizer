package org.algovisualizer;

import org.algovisualizer.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the Algorithm Visualizer application.
 * Provides interactive visualization of common algorithms with step-by-step execution.
 *
 * @author Algorithm Visualizer Team
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to set look and feel: " + e.getMessage());
            }
            new MainFrame().setVisible(true);
        });
    }
}