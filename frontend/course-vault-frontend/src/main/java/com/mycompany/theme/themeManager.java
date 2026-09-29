package com.mycompany.theme;

/**
 *
 * @author Abreham
 */
public class themeManager {
    private static boolean darkMode = true;
    public static boolean isDarkMode() {
        return darkMode;
    }
    public static void toggleTheme() {
        darkMode = !darkMode;
    }
    public static void setDarkMode(boolean value) {
        darkMode = value;
    }
}
