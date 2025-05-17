package com.lhmd.rechnerarchitektur.configuration;

import java.util.*;

public class UserConfig {
    private long executionInterval = 200;
    private double clock = 4;
    private String theme = "Primer Light";
    private List<String> fileHistory;

    public long getExecutionInterval() {
        return executionInterval;
    }

    public void setExecutionInterval(long executionInterval) {
        this.executionInterval = executionInterval;
    }

    public double getClock() {
        return clock;
    }

    public void setClock(double clock) {
        this.clock = clock;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getFileHistory() {
        if (fileHistory == null) {
            fileHistory = new ArrayList<>();
        }

        return fileHistory;
    }

    public void setFileHistory(List<String> fileHistory) {
        this.fileHistory = Objects.requireNonNull(fileHistory);
    }
}
