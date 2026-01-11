package org.algovisualizer.ui;

import org.algovisualizer.model.AlgorithmState;
import org.algovisualizer.model.AlgorithmType;
import org.algovisualizer.observer.AlgorithmObserver;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CodePanel extends JPanel implements AlgorithmObserver {
    private final RSyntaxTextArea textArea;
    private static final Map<AlgorithmType, String> codeMap = new HashMap<>();

    public CodePanel() {
        setLayout(new BorderLayout());
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setEditable(false);
        textArea.setHighlightCurrentLine(false);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        loadCodeFiles();
    }

    private void loadCodeFiles() {
        for (AlgorithmType type : AlgorithmType.values()) {
            String path = "/code/" + type.name() + ".java";
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is != null) {
                    String code = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                    codeMap.put(type, code);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateContent(AlgorithmType type) {
        textArea.setText(codeMap.getOrDefault(type, "// Code not available."));
        textArea.setCaretPosition(0);
    }

    @Override
    public void onStateUpdate(AlgorithmState state) {
        if (state.getAlgorithmType() != null) {
            updateContent(state.getAlgorithmType());
        }
        highlightLine(state.getHighlightedLine());
    }

    private void highlightLine(int line) {
        try {
            textArea.removeAllLineHighlights();
            if (line > 0) {
                textArea.addLineHighlight(line - 1, new Color(255, 255, 0, 100));
            }
        } catch (BadLocationException e) {
            // Ignore error
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