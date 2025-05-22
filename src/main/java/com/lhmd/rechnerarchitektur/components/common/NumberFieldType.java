package com.lhmd.rechnerarchitektur.components.common;

import java.util.regex.Pattern;

public enum NumberFieldType {
    INTEGER("\\d*"),
    FLOATING("\\d*(\\.\\d*)?");

    private final Pattern pattern;

    NumberFieldType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public Pattern pattern() {
        return pattern;
    }
}
