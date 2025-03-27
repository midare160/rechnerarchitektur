package com.lhmd.rechnerarchitektur;

import atlantafx.base.theme.*;
import javafx.application.Application;

import java.util.*;

public class ThemeManager {
    private static final String DEFAULT_THEME = "Primer Light";

    private static Map<String, String> themeMap;

    public static Map<String, String> getAllThemes() {
        return themeMap;
    }

    public static String getCurrentThemeName() {
        return Configuration.getTheme();
    }

    public static void setCurrentThemeName(String value) {
        var themePath = themeMap.get(value);

        if (themePath == null) {
            value = DEFAULT_THEME;
            themePath = themeMap.get(value);
        }

        Application.setUserAgentStylesheet(themePath);
        Configuration.setTheme(value);
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
            themeMap.put(theme.getName(), theme.getUserAgentStylesheet());
        }

        themeMap = Collections.unmodifiableMap(themeMap);
    }
}
