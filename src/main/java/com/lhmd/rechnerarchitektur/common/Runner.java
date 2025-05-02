package com.lhmd.rechnerarchitektur.common;

import java.util.function.Supplier;

public class Runner {
    private Runner() {
    }

    public static void unchecked(ThrowingRunnable runnable) {
        runnable.run();
    }

    public static <T> T unchecked(ThrowingSupplier<T> supplier) {
        return supplier.get();
    }

    @FunctionalInterface
    public interface ThrowingRunnable extends Runnable {
        @Override
        default void run() {
            try {
                runThrows();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void runThrows() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> extends Supplier<T> {
        @Override
        default T get() {
            try {
                return getThrows();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        T getThrows() throws Exception;
    }
}
