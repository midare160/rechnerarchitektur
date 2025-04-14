package com.lhmd.rechnerarchitektur.changes;

public class ChangeManager {
    private boolean isChanging;

    public BooleanTempValue beginChange() {
        return new BooleanTempValue(isChanging, true, c -> isChanging = c);
    }

    public boolean isChanging() {
        return isChanging;
    }
}
