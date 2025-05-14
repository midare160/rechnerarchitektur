package com.lhmd.rechnerarchitektur.configuration;

import java.util.ArrayList;

public class UserConfig {
    private String theme;
    private ArrayList<String> fileHistory;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ArrayList<String> getFileHistory() {
        return fileHistory;
    }

    public void setFileHistory(ArrayList<String> fileHistory) {
        this.fileHistory = fileHistory;
    }
}
