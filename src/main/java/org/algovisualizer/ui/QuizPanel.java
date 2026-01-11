package org.algovisualizer.ui;

import org.algovisualizer.model.quiz.Question;
import org.algovisualizer.model.quiz.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizPanel extends JPanel {
    private final Quiz quiz;
    private final JLabel questionLabel;
    private final ButtonGroup optionsGroup;
    private final JPanel optionsPanel;
    private final JButton submitButton;
    private final JLabel feedbackLabel;

    public QuizPanel(Quiz quiz) {
        this.quiz = quiz;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionLabel = new JLabel("Question", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsGroup = new ButtonGroup();

        submitButton = new JButton("Submit");
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(submitButton, BorderLayout.CENTER);
        southPanel.add(feedbackLabel, BorderLayout.SOUTH);

        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(new SubmitListener());
        displayNextQuestion();
    }

    private void displayNextQuestion() {
        Question currentQuestion = quiz.getCurrentQuestion();
        if (currentQuestion == null) {
            showFinalScore();
            return;
        }

        questionLabel.setText("<html><div style='text-align: center;'>" + currentQuestion.getQuestionText() + "</div></html>");
        optionsPanel.removeAll();
        optionsGroup.clearSelection();

        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            JRadioButton optionButton = new JRadioButton(options[i]);
            optionButton.setActionCommand(String.valueOf(i));
            optionsGroup.add(optionButton);
            optionsPanel.add(optionButton);
        }

        feedbackLabel.setText(" ");
        submitButton.setEnabled(true);
        revalidate();
        repaint();
    }

    private void showFinalScore() {
        questionLabel.setText("Quiz Finished!");
        optionsPanel.removeAll();
        submitButton.setEnabled(false);
        feedbackLabel.setText("Your final score: " + quiz.getScore() + " / " + quiz.getTotalQuestions());
    }

    private class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonModel selectedModel = optionsGroup.getSelection();
            if (selectedModel == null) {
                JOptionPane.showMessageDialog(QuizPanel.this, "Please select an answer.", "No Answer Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int selectedIndex = Integer.parseInt(selectedModel.getActionCommand());
            boolean isCorrect = quiz.submitAnswer(selectedIndex);

            if (isCorrect) {
                feedbackLabel.setText("Correct!");
                feedbackLabel.setForeground(new Color(0, 128, 0)); // Green
            } else {
                feedbackLabel.setText("Incorrect. Try the next one!");
                feedbackLabel.setForeground(Color.RED);
            }

            // Pause for a moment before showing the next question
            Timer timer = new Timer(1000, ae -> displayNextQuestion());
            timer.setRepeats(false);
            timer.start();
        }
    }
}