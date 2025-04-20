package com.lhmd.rechnerarchitektur.common;

public class Runner {
    private Runner() {
    }

    public static void unchecked(ThrowingRunnable runnable) {
        runnable.run();
    }
}
