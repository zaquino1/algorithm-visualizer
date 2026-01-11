package org.algovisualizer.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeManager {
    private static final ThemeManager INSTANCE = new ThemeManager();
    private final List<JFrame> openFrames = new ArrayList<>();

    private ThemeManager() {}

    public static ThemeManager getInstance() {
        return INSTANCE;
    }

    public void registerFrame(JFrame frame) {
        openFrames.add(frame);
    }

    public void unregisterFrame(JFrame frame) {
        openFrames.remove(frame);
    }

    public void applyTheme(String themeKey) {
        try {
            // 1. Set the base Look and Feel
            if (themeKey.equals("light")) {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }

            // 2. Apply detailed, high-contrast color overrides
            applyColorOverrides(themeKey);

            // 3. Update all open windows
            updateAllFrames();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyColorOverrides(String themeKey) {
        // Reset to defaults first to avoid color bleeding between themes
        resetColorOverrides();

        if ("terminal".equals(themeKey)) {
            // High-contrast green on near-black
            Color background = new Color(20, 20, 20);
            Color foreground = new Color(0, 255, 70);
            Color componentBg = new Color(35, 35, 35);

            UIManager.put("Panel.background", background);
            UIManager.put("View.background", background);
            UIManager.put("TabbedPane.background", background);
            UIManager.put("MenuBar.background", componentBg);
            
            UIManager.put("Button.background", componentBg);
            UIManager.put("Button.foreground", foreground);
            
            UIManager.put("Label.foreground", foreground);
            UIManager.put("TitledBorder.titleColor", foreground);
            
            UIManager.put("ComboBox.background", componentBg);
            UIManager.put("ComboBox.foreground", foreground);
            
            UIManager.put("JSpinner.background", componentBg);
            UIManager.put("JSpinner.foreground", foreground);

            UIManager.put("text", foreground);
            UIManager.put("TextField.foreground", foreground);
            UIManager.put("TextArea.foreground", foreground);
        }
        // "Dark" and "Light" themes will use the robust FlatLaf defaults,
        // which already have good contrast. No overrides needed.
    }

    private void resetColorOverrides() {
        UIManager.put("Panel.background", null);
        UIManager.put("View.background", null);
        UIManager.put("TabbedPane.background", null);
        UIManager.put("MenuBar.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Label.foreground", null);
        UIManager.put("TitledBorder.titleColor", null);
        UIManager.put("ComboBox.background", null);
        UIManager.put("ComboBox.foreground", null);
        UIManager.put("JSpinner.background", null);
        UIManager.put("JSpinner.foreground", null);
        UIManager.put("text", null);
        UIManager.put("TextField.foreground", null);
        UIManager.put("TextArea.foreground", null);
    }

    private void updateAllFrames() {
        for (JFrame frame : new ArrayList<>(openFrames)) {
            SwingUtilities.updateComponentTreeUI(frame);
            if (frame instanceof VisualizerFrame) {
                ((VisualizerFrame) frame).updateChildThemes();
            }
        }
    }
}