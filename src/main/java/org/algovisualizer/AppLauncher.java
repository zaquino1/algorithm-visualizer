package org.algovisualizer;

import org.algovisualizer.ui.LmsFrame;
import org.algovisualizer.ui.ThemeManager;
import org.algovisualizer.ui.VisualizerFrame;

import javax.swing.*;
import java.awt.*;

public class AppLauncher {

    public static void main(String[] args) {
        // Set the default theme on startup
        ThemeManager.getInstance().applyTheme("terminal");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Algorithm Visualizer - Main Menu");
            ThemeManager.getInstance().registerFrame(frame); // Register with ThemeManager
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    ThemeManager.getInstance().unregisterFrame(frame);
                }
            });

            JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel("Algorithm Visualizer", SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            JPanel categoryPanel = new JPanel(new GridLayout(0, 1, 10, 10));
            categoryPanel.setBorder(BorderFactory.createTitledBorder("Visualizers"));
            
            String[] categories = {"Sorting", "Searching", "Graph", "Tree", "Strings"};
            for (String category : categories) {
                JButton button = new JButton(category);
                button.setFont(new Font("SansSerif", Font.PLAIN, 18));
                button.addActionListener(e -> new VisualizerFrame(category).setVisible(true));
                categoryPanel.add(button);
            }
            mainPanel.add(categoryPanel, BorderLayout.CENTER);

            JPanel lmsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton lmsButton = new JButton("LMS & Quiz Center");
            lmsButton.setFont(new Font("SansSerif", Font.BOLD, 18));
            lmsButton.addActionListener(e -> new LmsFrame().setVisible(true));
            lmsPanel.add(lmsButton);
            mainPanel.add(lmsPanel, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}