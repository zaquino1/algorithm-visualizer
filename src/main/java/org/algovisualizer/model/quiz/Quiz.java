package org.algovisualizer.model.quiz;

import org.algovisualizer.model.AlgorithmType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz {
    private static final Map<AlgorithmType, List<Question>> questions = new HashMap<>();
    private final AlgorithmType algorithmType;
    private final List<Question> quizQuestions;
    private int currentQuestionIndex;
    private int score;

    static {
        // --- Bubble Sort Questions ---
        List<Question> bubbleSortQuestions = new ArrayList<>();
        bubbleSortQuestions.add(new Question("What is the worst-case time complexity of Bubble Sort?", new String[]{"O(n)", "O(n log n)", "O(n²)", "O(1)"}, 2));
        bubbleSortQuestions.add(new Question("When does the best-case scenario for Bubble Sort occur?", new String[]{"When the array is sorted in reverse", "When the array is already sorted", "When the array contains random elements", "When all elements are the same"}, 1));
        questions.put(AlgorithmType.BUBBLE_SORT, bubbleSortQuestions);

        // --- Quick Sort Questions ---
        List<Question> quickSortQuestions = new ArrayList<>();
        quickSortQuestions.add(new Question("What is the average time complexity of Quick Sort?", new String[]{"O(n)", "O(n log n)", "O(n²)", "O(log n)"}, 1));
        quickSortQuestions.add(new Question("Which of the following is a key step in Quick Sort?", new String[]{"Merging", "Heapifying", "Partitioning", "Counting"}, 2));
        questions.put(AlgorithmType.QUICK_SORT, quickSortQuestions);
        
        // --- Add more questions for other algorithms here ---
    }

    public Quiz(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
        this.quizQuestions = new ArrayList<>(questions.getOrDefault(algorithmType, new ArrayList<>()));
        Collections.shuffle(this.quizQuestions);
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public Question getCurrentQuestion() {
        if (isFinished()) {
            return null;
        }
        return quizQuestions.get(currentQuestionIndex);
    }

    public boolean submitAnswer(int answerIndex) {
        if (isFinished()) {
            return false;
        }
        boolean isCorrect = getCurrentQuestion().isCorrect(answerIndex);
        if (isCorrect) {
            score++;
        }
        currentQuestionIndex++;
        return isCorrect;
    }

    public boolean isFinished() {
        return currentQuestionIndex >= quizQuestions.size();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return quizQuestions.size();
    }
}