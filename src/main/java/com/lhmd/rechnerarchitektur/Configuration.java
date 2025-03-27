package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.Common.StringUtils;

import java.io.*;
import java.util.*;

public class Configuration {
    private static final String FILE_NAME = "config.properties";
    private static final String SPLIT_SEPARATOR = "\0";

    private static final String THEME = "theme";
    private static final String RECENT_FILES = "recentFiles";

    private static Properties properties;

    public static synchronized void initialize() throws IOException {
        if (properties != null) {
            throw new IllegalStateException("Configuration is already initialized");
        }

        properties = new Properties();

        var file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (var inputStream = new FileInputStream(file)) {
            properties.load(inputStream);
        }
    }

    public static synchronized void save() throws IOException {
        try (var inputStream = new FileOutputStream(FILE_NAME)) {
            properties.store(inputStream, ProgramInfo.PROGRAM_NAME + " Configuration");
        }
    }

    public static String getTheme() {
        return properties.getProperty(THEME);
    }

    public static void setTheme(String theme) {
        properties.setProperty(THEME, theme);
    }

    public static String[] getRecentFiles() {
        var recentFiles = properties.getProperty(RECENT_FILES);

        if (StringUtils.isNullOrEmpty(recentFiles)) {
            return new String[0];
        }

        return properties.getProperty(RECENT_FILES).split(SPLIT_SEPARATOR);
    }

    public static void addRecentFile(String filePath) {
        var recentFiles = new ArrayList<>(Arrays.asList(getRecentFiles()));

        // The most recent file should always be on top
        recentFiles.removeIf(f -> f.equals(filePath));
        recentFiles.addFirst(filePath);

        properties.setProperty(RECENT_FILES, String.join(SPLIT_SEPARATOR, recentFiles));
    }
}
