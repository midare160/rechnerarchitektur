package com.lhmd.rechnerarchitektur.styles;

import atlantafx.base.theme.*;
import com.lhmd.rechnerarchitektur.JavaFxApplication;
import com.lhmd.rechnerarchitektur.configuration.*;
import javafx.application.Application;
import javafx.scene.Scene;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Lazy
public class ThemeManager {
    @SuppressWarnings("DataFlowIssue")
    private static final String LIGHTSTYLE_URL = JavaFxApplication.class.getResource("styles/theme-light.css").toExternalForm();
    @SuppressWarnings("DataFlowIssue")
    private static final String DARKSTYLE_URL = JavaFxApplication.class.getResource("styles/theme-dark.css").toExternalForm();

    private final UserConfig userConfig;
    private final Map<String, Theme> themes;
    private final WeakHashMap<Scene, Void> scenes;

    public ThemeManager(UserConfigService userConfigService) {
        this.userConfig = userConfigService.config();
        this.themes = createThemeMap();
        this.scenes = new WeakHashMap<>();

        setApplicationStylesheet();
    }

    public Map<String, Theme> themes() {
        return themes;
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
        scenes.put(scene, null);
        applyCurrentStylesheet(scene);
    }

    private Map<String, Theme> createThemeMap() {
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

        return Collections.unmodifiableMap(map);
    }

    private void setApplicationStylesheet() {
        var theme = themes.get(getCurrentThemeName());
        Application.setUserAgentStylesheet(theme.getUserAgentStylesheet());
    }

    private void applyCurrentStylesheet(Scene scene) {
        var currentTheme = themes.get(getCurrentThemeName());

        var oldStyleUrl = currentTheme.isDarkMode() ? LIGHTSTYLE_URL : DARKSTYLE_URL;
        var newStyleUrl = currentTheme.isDarkMode() ? DARKSTYLE_URL : LIGHTSTYLE_URL;

        scene.getStylesheets().remove(oldStyleUrl);
        scene.getStylesheets().add(newStyleUrl);
    }

    private void onThemeChanged() {
        for (var scene : scenes.keySet()) {
            applyCurrentStylesheet(scene);
        }
    }
}
