package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.AlgorithmType;
import org.algovisualizer.model.quiz.Quiz;
import org.algovisualizer.observer.AlgorithmObserver;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InfoPanel extends JPanel implements AlgorithmObserver {
    private JEditorPane infoEditorPane;
    private JButton quizButton;
    private AlgorithmType currentAlgorithmType;
    private static final Map<AlgorithmType, String> algoInfo = new HashMap<>();

    static {
        // Sorting
        algoInfo.put(AlgorithmType.BUBBLE_SORT, "<html><h3>Bubble Sort</h3><p>A simple sorting algorithm that repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order. The pass through the list is repeated until the list is sorted.</p><p><b>Time Complexity:</b> O(n²)</p><p><b>Space Complexity:</b> O(1)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Bubble_sort'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.SELECTION_SORT, "<html><h3>Selection Sort</h3><p>An in-place comparison sorting algorithm. It has an O(n²) time complexity, which makes it inefficient on large lists, and generally performs worse than the similar insertion sort.</p><p><b>Time Complexity:</b> O(n²)</p><p><b>Space Complexity:</b> O(1)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Selection_sort'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.INSERTION_SORT, "<html><h3>Insertion Sort</h3><p>A simple sorting algorithm that builds the final sorted array one item at a time. It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.</p><p><b>Time Complexity:</b> O(n²)</p><p><b>Space Complexity:</b> O(1)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Insertion_sort'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.MERGE_SORT, "<html><h3>Merge Sort</h3><p>An efficient, stable, comparison-based sorting algorithm following the divide-and-conquer paradigm.</p><p><b>Time Complexity:</b> O(n log n)</p><p><b>Space Complexity:</b> O(n)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Merge_sort'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.QUICK_SORT, "<html><h3>Quick Sort</h3><p>An efficient sorting algorithm that uses partitioning to recursively divide the array into smaller sub-arrays.</p><p><b>Time Complexity:</b> O(n log n) average, O(n²) worst-case</p><p><b>Space Complexity:</b> O(log n)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Quicksort'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.HEAP_SORT, "<html><h3>Heap Sort</h3><p>A comparison-based sorting algorithm that uses a binary heap data structure.</p><p><b>Time Complexity:</b> O(n log n)</p><p><b>Space Complexity:</b> O(1)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Heapsort'>Wikipedia</a></p></html>");
        
        // Searching
        algoInfo.put(AlgorithmType.LINEAR_SEARCH, "<html><h3>Linear Search</h3><p>A method for finding an element within a list. It sequentially checks each element of the list until a match is found or the whole list has been searched.</p><p><b>Time Complexity:</b> O(n)</p><p><b>Space Complexity:</b> O(1)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Linear_search'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.BINARY_SEARCH, "<html><h3>Binary Search</h3><p>A search algorithm that finds the position of a target value within a sorted array. Binary search compares the target value to the middle element of the array.</p><p><b>Time Complexity:</b> O(log n)</p><p><b>Space Complexity:</b> O(1)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Binary_search_algorithm'>Wikipedia</a></p></html>");

        // Graph
        algoInfo.put(AlgorithmType.BFS, "<html><h3>Breadth-First Search (BFS)</h3><p>An algorithm for traversing or searching tree or graph data structures. It starts at the tree root and explores all of the neighbor nodes at the present depth prior to moving on to the nodes at the next depth level.</p><p><b>Time Complexity:</b> O(V + E)</p><p><b>Space Complexity:</b> O(V)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Breadth-first_search'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.DFS, "<html><h3>Depth-First Search (DFS)</h3><p>An algorithm for traversing or searching tree or graph data structures. The algorithm starts at the root node and explores as far as possible along each branch before backtracking.</p><p><b>Time Complexity:</b> O(V + E)</p><p><b>Space Complexity:</b> O(V)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Depth-first_search'>Wikipedia</a></p></html>");
        algoInfo.put(AlgorithmType.DIJKSTRA, "<html><h3>Dijkstra's Algorithm</h3><p>An algorithm for finding the shortest paths between nodes in a graph, which may represent, for example, road networks.</p><p><b>Time Complexity:</b> O(E log V)</p><p><b>Space Complexity:</b> O(V)</p><p><b>Learn more:</b> <a href='https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm'>Wikipedia</a></p></html>");

        // Tree
        algoInfo.put(AlgorithmType.IN_ORDER_TRAVERSAL, "<html><h3>In-Order Traversal</h3><p>A method of traversing a binary tree where the left subtree is visited first, then the root, then the right subtree. This traversal visits the nodes in ascending order in a binary search tree.</p><p><b>Time Complexity:</b> O(n)</p><p><b>Space Complexity:</b> O(h)</p></html>");
        algoInfo.put(AlgorithmType.PRE_ORDER_TRAVERSAL, "<html><h3>Pre-Order Traversal</h3><p>A method of traversing a binary tree where the root is visited first, then the left subtree, then the right subtree. It's useful for creating a copy of the tree.</p><p><b>Time Complexity:</b> O(n)</p><p><b>Space Complexity:</b> O(h)</p></html>");
        algoInfo.put(AlgorithmType.POST_ORDER_TRAVERSAL, "<html><h3>Post-Order Traversal</h3><p>A method of traversing a binary tree where the left subtree is visited first, then the right subtree, and finally the root. It's useful for deleting nodes from the tree.</p><p><b>Time Complexity:</b> O(n)</p><p><b>Space Complexity:</b> O(h)</p></html>");
    }

    public InfoPanel() {
        setLayout(new BorderLayout());
        
        infoEditorPane = new JEditorPane();
        infoEditorPane.setContentType("text/html");
        infoEditorPane.setEditable(false);
        infoEditorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(infoEditorPane);
        
        quizButton = new JButton("Take a Quiz!");
        quizButton.addActionListener(e -> openQuizWindow());
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(quizButton);

        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void openQuizWindow() {
        if (currentAlgorithmType == null) return;

        Quiz quiz = new Quiz(currentAlgorithmType);
        if (quiz.getTotalQuestions() == 0) {
            JOptionPane.showMessageDialog(this, "No quiz available for this algorithm yet.", "Quiz Not Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        QuizPanel quizPanel = new QuizPanel(quiz);
        JDialog quizDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Quiz: " + currentAlgorithmType.toString(), Dialog.ModalityType.APPLICATION_MODAL);
        quizDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        quizDialog.setContentPane(quizPanel);
        quizDialog.setSize(500, 400);
        quizDialog.setLocationRelativeTo(this);
        quizDialog.setVisible(true);
    }

    public void updateContent(AlgorithmType type) {
        this.currentAlgorithmType = type;
        infoEditorPane.setText(algoInfo.getOrDefault(type, "<html><h3>Information</h3><p>Documentation for this algorithm is not available yet.</p></html>"));
        infoEditorPane.setCaretPosition(0); // Scroll to top
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        if (state.getAlgorithmType() != null) {
            updateContent(state.getAlgorithmType());
        }
    }
}