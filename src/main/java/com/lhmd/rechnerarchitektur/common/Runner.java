package com.lhmd.rechnerarchitektur.common;

public class Runner {
    private Runner() {
    }

    public static void unchecked(ThrowingRunnable runnable) {
        unchecked(() -> {
            runnable.run();
            return (Void) null;
        });
    }

    public static <T> T unchecked(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }
}
