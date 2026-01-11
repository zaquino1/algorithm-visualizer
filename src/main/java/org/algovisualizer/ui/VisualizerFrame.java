package org.algovisualizer.ui;

import org.algovisualizer.controller.AlgorithmController;
import org.algovisualizer.model.AlgorithmType;
import org.algovisualizer.ui.MusicManager.PlaybackMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.stream.Collectors;

public class VisualizerFrame extends JFrame {
    private static int openWindowCount = 0;

    private final String category;
    private final AlgorithmController controller;
    private final ControlPanel controlPanel;
    private final InfoPanel infoPanel;
    private final CodePanel codePanel;
    private final TranslationPanel translationPanel;
    private final MusicManager musicManager;
    private final ThemeManager themeManager;

    private final JPanel visualizerContainer;
    private final CardLayout visualizerLayout;
    
    private final VisualizationPanel barVisualizer;
    private final GraphVisualizerPanel graphVisualizer;
    private final TreeVisualizerPanel treeVisualizer;

    public VisualizerFrame(String category) {
        this.category = category;
        
        this.controller = new AlgorithmController();
        this.musicManager = MusicManager.getInstance();
        this.themeManager = ThemeManager.getInstance();
        this.barVisualizer = new VisualizationPanel();
        this.graphVisualizer = new GraphVisualizerPanel();
        this.treeVisualizer = new TreeVisualizerPanel();
        this.infoPanel = new InfoPanel();
        this.codePanel = new CodePanel();
        this.translationPanel = new TranslationPanel();
        this.controlPanel = new ControlPanel(controller, getAlgorithmsForCategory(category), category);

        this.visualizerLayout = new CardLayout();
        this.visualizerContainer = new JPanel(visualizerLayout);
        setupVisualizersLayout();

        JTabbedPane infoTabbedPane = new JTabbedPane();
        infoTabbedPane.addTab("Information", infoPanel);
        infoTabbedPane.addTab("Code", codePanel);
        infoTabbedPane.addTab("Translate", translationPanel);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, visualizerContainer, infoTabbedPane);
        mainSplitPane.setResizeWeight(0.65);
        mainSplitPane.setOneTouchExpandable(true);

        initializeUI(mainSplitPane);

        controller.addObserver(barVisualizer);
        controller.addObserver(graphVisualizer);
        controller.addObserver(treeVisualizer);
        controller.addObserver(controlPanel);
        controller.addObserver(infoPanel);
        controller.addObserver(codePanel);
        controller.addVisualizerSwitcher(this::switchVisualizer);
        
        themeManager.registerFrame(this);
        
        AlgorithmType defaultAlgo = getAlgorithmsForCategory(category)[0];
        controller.setAlgorithm(defaultAlgo);
        translationPanel.setAlgorithm(defaultAlgo);

        openWindowCount++;
    }

    private void initializeUI(Component centerComponent) {
        setTitle("Algorithm Visualizer - " + category);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 800));
        setSize(1600, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setJMenuBar(createMenuBar());
        add(centerComponent, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                openWindowCount--;
                themeManager.unregisterFrame(VisualizerFrame.this);
                if (openWindowCount == 0) {
                    musicManager.stop();
                }
            }
        });
    }
    
    private void setupVisualizersLayout() {
        visualizerContainer.add(barVisualizer, "Sorting");
        visualizerContainer.add(barVisualizer, "Searching");
        visualizerContainer.add(graphVisualizer, "Graph");
        visualizerContainer.add(treeVisualizer, "Tree");
        visualizerContainer.add(createPlaceholderVisualizer("String Visualizer Coming Soon"), "Strings");
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem closeItem = new JMenuItem("Close Window");
        closeItem.addActionListener(e -> dispose());
        fileMenu.add(closeItem);
        menuBar.add(fileMenu);

        JMenu themeMenu = new JMenu("Theme");
        addThemeMenuItem(themeMenu, "Terminal", "terminal");
        addThemeMenuItem(themeMenu, "Dark", "dark");
        addThemeMenuItem(themeMenu, "Light", "light");
        menuBar.add(themeMenu);

        if (category.equals("Sorting") || category.equals("Searching")) {
            JMenu appearanceMenu = new JMenu("Appearance");
            JMenuItem colorItem = new JMenuItem("Change Bar Color...");
            colorItem.addActionListener(e -> {
                Color newColor = JColorChooser.showDialog(this, "Choose Bar Color", barVisualizer.getNormalColor());
                if (newColor != null) {
                    barVisualizer.setNormalColor(newColor);
                }
            });
            appearanceMenu.add(colorItem);
            menuBar.add(appearanceMenu);
        }

        JMenu musicMenu = new JMenu("Music");
        JMenuItem lofiItem = new JMenuItem("Play Lofi");
        lofiItem.addActionListener(e -> musicManager.play(0));
        JMenuItem classicalItem = new JMenuItem("Play Classical");
        classicalItem.addActionListener(e -> musicManager.play(1));
        JMenuItem instrumentalItem = new JMenuItem("Play Instrumental");
        instrumentalItem.addActionListener(e -> musicManager.play(2));
        JMenuItem jazzItem = new JMenuItem("Play Jazz");
        jazzItem.addActionListener(e -> musicManager.play(3));
        
        JMenu modeMenu = new JMenu("Playback Mode");
        ButtonGroup modeGroup = new ButtonGroup();
        JRadioButtonMenuItem noRepeatItem = new JRadioButtonMenuItem("No Repeat", true);
        noRepeatItem.addActionListener(e -> musicManager.setMode(PlaybackMode.NO_REPEAT));
        JRadioButtonMenuItem repeatOneItem = new JRadioButtonMenuItem("Repeat One");
        repeatOneItem.addActionListener(e -> musicManager.setMode(PlaybackMode.REPEAT_ONE));
        JRadioButtonMenuItem repeatAllItem = new JRadioButtonMenuItem("Repeat All");
        repeatAllItem.addActionListener(e -> musicManager.setMode(PlaybackMode.REPEAT_ALL));
        JRadioButtonMenuItem shuffleItem = new JRadioButtonMenuItem("Shuffle");
        shuffleItem.addActionListener(e -> musicManager.setMode(PlaybackMode.SHUFFLE));
        
        modeGroup.add(noRepeatItem);
        modeGroup.add(repeatOneItem);
        modeGroup.add(repeatAllItem);
        modeGroup.add(shuffleItem);
        modeMenu.add(noRepeatItem);
        modeMenu.add(repeatOneItem);
        modeMenu.add(repeatAllItem);
        modeMenu.add(shuffleItem);

        JMenuItem stopItem = new JMenuItem("Stop Music");
        stopItem.addActionListener(e -> musicManager.stop());

        musicMenu.add(lofiItem);
        musicMenu.add(classicalItem);
        musicMenu.add(instrumentalItem);
        musicMenu.add(jazzItem);
        musicMenu.addSeparator();
        musicMenu.add(modeMenu);
        musicMenu.addSeparator();
        musicMenu.add(stopItem);
        menuBar.add(musicMenu);

        return menuBar;
    }

    private void addThemeMenuItem(JMenu menu, String name, String themeKey) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> themeManager.applyTheme(themeKey));
        menu.add(item);
    }
    
    public void updateChildThemes() {
        String lafName = UIManager.getLookAndFeel().getName();
        boolean isDark = lafName.contains("Dark") || lafName.contains("Terminal");
        if (codePanel != null) codePanel.setTheme(isDark);
        if (translationPanel != null) translationPanel.setTheme(isDark);
    }
    
    public void switchVisualizer(String category) {
        visualizerLayout.show(visualizerContainer, category);
    }
    
    private JPanel createPlaceholderVisualizer(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(message);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(label);
        return panel;
    }

    private AlgorithmType[] getAlgorithmsForCategory(String category) {
        return Arrays.stream(AlgorithmType.values())
                .filter(type -> type.getCategory().equals(category))
                .toArray(AlgorithmType[]::new);
    }
}