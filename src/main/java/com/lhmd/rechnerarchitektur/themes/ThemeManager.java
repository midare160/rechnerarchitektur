package com.lhmd.rechnerarchitektur.themes;

import atlantafx.base.theme.*;
import com.lhmd.rechnerarchitektur.JavaFxApplication;
import com.lhmd.rechnerarchitektur.configuration.*;
import javafx.application.Application;
import javafx.scene.Scene;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ThemeManager {
    private static final Map<String, Theme> themeMap;

    static {
        var themes = new Theme[]{
                new PrimerLight(),
                new PrimerDark(),
                new NordLight(),
                new NordDark(),
                new CupertinoLight(),
                new CupertinoDark(),
                new Dracula(),
        };

        var map = new LinkedHashMap<String, Theme>();

        for (var theme : themes) {
            map.put(theme.getName(), theme);
        }

        themeMap = Collections.unmodifiableMap(map);
    }

    public static Map<String, Theme> allThemes() {
        return themeMap;
    }

    private final UserConfig userConfig;
    private final List<Scene> scenes;

    public ThemeManager(UserConfigService userConfigService) {
        userConfig = userConfigService.config();
        scenes = new ArrayList<>();

        setApplicationStylesheet();
    }

    public String getCurrentThemeName() {
        return userConfig.getTheme();
    }

    public void setCurrentThemeName(String value) {
        userConfig.setTheme(value);

        setApplicationStylesheet();
        onThemeChanged();
    }

    public void registerScene(Scene scene) {
        scenes.add(scene);
        applyCurrentStylesheet(scene);
    }

    private void setApplicationStylesheet() {
        var theme = themeMap.get(getCurrentThemeName());
        Application.setUserAgentStylesheet(theme.getUserAgentStylesheet());
    }

    private void applyCurrentStylesheet(Scene scene) {
        // TODO maybe get injected from DI?
        final var lightStyleUrl = JavaFxApplication.class.getResource("styles/theme-light.css").toExternalForm();
        final var darkStyleUrl = JavaFxApplication.class.getResource("styles/theme-dark.css").toExternalForm();

        var currentTheme = themeMap.get(getCurrentThemeName());

        var oldStyleUrl = currentTheme.isDarkMode() ? lightStyleUrl : darkStyleUrl;
        var newStyleUrl = currentTheme.isDarkMode() ? darkStyleUrl : lightStyleUrl;

        scene.getStylesheets().remove(oldStyleUrl);
        scene.getStylesheets().add(newStyleUrl);
    }

    private void onThemeChanged() {
        for (var scene : scenes) {
            applyCurrentStylesheet(scene);
        }
    }
}
