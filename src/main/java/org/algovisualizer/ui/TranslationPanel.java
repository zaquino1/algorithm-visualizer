package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmType;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TranslationPanel extends JPanel {
    private final RSyntaxTextArea textArea;
    private final JComboBox<String> languageSelector;
    private AlgorithmType currentAlgorithm;
    private static final Map<AlgorithmType, Map<String, String>> translationMap = new HashMap<>();

    public TranslationPanel() {
        setLayout(new BorderLayout());

        textArea = new RSyntaxTextArea();
        textArea.setEditable(false);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);

        languageSelector = new JComboBox<>(new String[]{"Python", "JavaScript", "C++"});
        languageSelector.addActionListener(e -> updateTranslation());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Translate to:"));
        topPanel.add(languageSelector);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadTranslations();
    }

    private void loadTranslations() {
        // This would be loaded from files in a real application
        // For now, we'll hardcode a simple Python translation for Bubble Sort
        Map<String, String> bubbleSortTranslations = new HashMap<>();
        bubbleSortTranslations.put("Python", 
            "def bubble_sort(arr):\n" +
            "    n = len(arr)\n" +
            "    for i in range(n):\n" +
            "        for j in range(0, n - i - 1):\n" +
            "            if arr[j] > arr[j + 1]:\n" +
            "                arr[j], arr[j + 1] = arr[j + 1], arr[j]"
        );
        translationMap.put(AlgorithmType.BUBBLE_SORT, bubbleSortTranslations);
    }

    public void setAlgorithm(AlgorithmType type) {
        this.currentAlgorithm = type;
        updateTranslation();
    }

    private void updateTranslation() {
        if (currentAlgorithm == null) return;

        String language = (String) languageSelector.getSelectedItem();
        String translatedCode = translationMap
                .getOrDefault(currentAlgorithm, new HashMap<>())
                .getOrDefault(language, "// Translation not available.");
        
        textArea.setText(translatedCode);
        setSyntaxStyle(language);
        textArea.setCaretPosition(0);
    }

    private void setSyntaxStyle(String language) {
        switch (language) {
            case "Python":
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
                break;
            case "JavaScript":
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
                break;
            case "C++":
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                break;
            default:
                textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE);
                break;
        }
    }

    public void setTheme(boolean isDark) {
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(isDark ? "/themes/dark.xml" : "/themes/light.xml"));
            theme.apply(textArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}