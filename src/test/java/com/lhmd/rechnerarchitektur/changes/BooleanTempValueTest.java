package com.lhmd.rechnerarchitektur.changes;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanTempValueTest {
    @Test
    public void constructor_initFalseTempTrue_setsToTrue() {
        var value = new AtomicBoolean(false);

        try (var ignored = new BooleanTempValue(value.get(), true, value::set)) {
            assertTrue(value.get());
        }

        assertFalse(value.get());
    }

    @Test
    public void constructor_initTrueTempFalse_setsToFalse() {
        var value = new AtomicBoolean(true);

        try (var ignored = new BooleanTempValue(value.get(), false, value::set)) {
            assertFalse(value.get());
        }

        assertTrue(value.get());
    }

    @Test
    public void constructor_initTrueTempTrue_doesNothing() {
        var value = new AtomicBoolean(true);

        try (var ignored = new BooleanTempValue(value.get(), true, value::set)) {
            assertTrue(value.get());
        }

        assertTrue(value.get());
    }

    @Test
    public void constructor_initFalseTempFalse_doesNothing() {
        var value = new AtomicBoolean(false);

        try (var ignored = new BooleanTempValue(value.get(), false, value::set)) {
            assertFalse(value.get());
        }

        assertFalse(value.get());
    }

    @Test
    public void constructor_initFalseTempTrueNested_doesNotOverride() {
        var value = new AtomicBoolean(false);

        try (var ignored = new BooleanTempValue(value.get(), true, value::set)) {
            assertTrue(value.get());

            try (var ignoredNested = new BooleanTempValue(value.get(), true, value::set)) {
                assertTrue(value.get());
            }

            assertTrue(value.get());
        }

        assertFalse(value.get());
    }
}
