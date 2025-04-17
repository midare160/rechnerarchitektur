package com.lhmd.rechnerarchitektur.common;

public class Runner {
    public static void ignoreExceptionWarning(ThrowingRunnable runnable) {
        runnable.run();
    }
}
