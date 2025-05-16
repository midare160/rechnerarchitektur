package com.lhmd.rechnerarchitektur.configuration;

import java.util.*;

public class UserConfig {
    private String theme;
    private List<String> fileHistory;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getFileHistory() {
        return fileHistory;
    }

    public void setFileHistory(List<String> fileHistory) {
        this.fileHistory = fileHistory;
    }
}
