package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmType;
import org.algovisualizer.model.quiz.Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LmsFrame extends JFrame {

    public LmsFrame() {
        setTitle("Learning Management System & Quiz Center");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane categoryTabs = new JTabbedPane();

        // Get distinct categories and create a tab for each
        List<String> categories = Arrays.stream(AlgorithmType.values())
                .map(AlgorithmType::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (String category : categories) {
            categoryTabs.addTab(category, createCategoryPanel(category));
        }

        add(categoryTabs);
    }

    private JScrollPane createCategoryPanel(String category) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Get algorithms for the current category
        List<AlgorithmType> algorithms = Arrays.stream(AlgorithmType.values())
                .filter(type -> type.getCategory().equals(category))
                .collect(Collectors.toList());

        for (AlgorithmType type : algorithms) {
            JButton quizButton = new JButton("Take Quiz: " + type.toString());
            quizButton.addActionListener(e -> openQuizWindow(type));
            panel.add(quizButton);
        }

        return new JScrollPane(panel);
    }

    private void openQuizWindow(AlgorithmType type) {
        Quiz quiz = new Quiz(type);
        if (quiz.getTotalQuestions() == 0) {
            JOptionPane.showMessageDialog(this, "No quiz available for this algorithm yet.", "Quiz Not Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        QuizPanel quizPanel = new QuizPanel(quiz);
        JDialog quizDialog = new JDialog(this, "Quiz: " + type.toString(), Dialog.ModalityType.APPLICATION_MODAL);
        quizDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        quizDialog.setContentPane(quizPanel);
        quizDialog.setSize(500, 400);
        quizDialog.setLocationRelativeTo(this);
        quizDialog.setVisible(true);
    }
}