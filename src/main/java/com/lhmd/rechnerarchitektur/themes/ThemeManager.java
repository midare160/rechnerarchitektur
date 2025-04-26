package com.lhmd.rechnerarchitektur.themes;

import atlantafx.base.theme.*;
import com.lhmd.rechnerarchitektur.Configuration;
import com.lhmd.rechnerarchitektur.Launcher;
import javafx.application.Application;
import javafx.scene.Scene;

import java.util.*;

public class ThemeManager {
    public static final String DEFAULT_THEME = "Primer Light";

    private static Map<String, Theme> themeMap;

    public static Map<String, Theme> allThemes() {
        return themeMap;
    }

    public static String getCurrentThemeName() {
        return Configuration.getTheme();
    }

    public static void setCurrentThemeName(String value) {
        var theme = themeMap.get(value);

        if (theme == null) {
            value = DEFAULT_THEME;
            theme = themeMap.get(value);
        }

        Application.setUserAgentStylesheet(theme.getUserAgentStylesheet());
        Configuration.setTheme(value);
    }

    public static void applyCurrentStylesheet(Scene scene) {
        final var lightStyleUrl = Launcher.class.getResource("styles/theme-light.css").toExternalForm();
        final var darkStyleUrl = Launcher.class.getResource("styles/theme-dark.css").toExternalForm();

        var currentTheme = themeMap.get(getCurrentThemeName());

        var oldStyleUrl = currentTheme.isDarkMode() ? lightStyleUrl : darkStyleUrl;;
        var newStyleUrl = currentTheme.isDarkMode() ? darkStyleUrl : lightStyleUrl;

        scene.getStylesheets().remove(oldStyleUrl);
        scene.getStylesheets().add(newStyleUrl);
    }

    public static void initialize() {
        initializeThemes();

        setCurrentThemeName(getCurrentThemeName());
    }

    private static void initializeThemes() {
        var themes = new Theme[]{
                new PrimerLight(),
                new PrimerDark(),
                new NordLight(),
                new NordDark(),
                new CupertinoLight(),
                new CupertinoDark(),
                new Dracula(),
        };

        themeMap = new LinkedHashMap<>();

        for (var theme : themes) {
            themeMap.put(theme.getName(), theme);
        }

        themeMap = Collections.unmodifiableMap(themeMap);
    }
}
