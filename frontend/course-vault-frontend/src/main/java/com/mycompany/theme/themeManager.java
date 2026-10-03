package com.mycompany.theme;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author Abreham
 */
public class themeManager {
    private static boolean darkMode = true;
    private static final List<Runnable> listeners = new ArrayList<>();
    public static boolean isDarkMode() {
        return darkMode;
    }
    public static void toggleTheme() {
        darkMode = !darkMode;
        notifyListeners();
    }
    public static void setDarkMode(boolean value) {
        darkMode = value;
    }
    public static void addListener(Runnable listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    public static void removeListener(Runnable listener) {
        listeners.remove(listener);
    }
    private static void notifyListeners() {
        Platform.runLater(() -> {
            for (Runnable listener : new ArrayList<>(listeners)) {
                listener.run();
            }
        });
    }
}
