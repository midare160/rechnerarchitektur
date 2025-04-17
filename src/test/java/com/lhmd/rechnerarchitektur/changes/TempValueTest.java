package com.lhmd.rechnerarchitektur.changes;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

public class TempValueTest {
    @Test
    public void constructor_temporaryInt_resetsCorrectly() {
        var value = new AtomicInteger(69);

        try (var ignored = new TempValue<>(value.get(), 420, value::set)) {
            assertEquals(420, value.get());
        }

        assertEquals(69, value.get());
    }

    @Test
    public void constructor_initFalseTempTrue_setsToTrue() {
        var value = new AtomicBoolean(false);

        try (var ignored = new TempValue<>(value.get(), true, value::set)) {
            assertTrue(value.get());
        }

        assertFalse(value.get());
    }

    @Test
    public void constructor_initTrueTempFalse_setsToFalse() {
        var value = new AtomicBoolean(true);

        try (var ignored = new TempValue<>(value.get(), false, value::set)) {
            assertFalse(value.get());
        }

        assertTrue(value.get());
    }

    @Test
    public void constructor_initTrueTempTrue_doesNothing() {
        var value = new AtomicBoolean(true);

        try (var ignored = new TempValue<>(value.get(), true, value::set)) {
            assertTrue(value.get());
        }

        assertTrue(value.get());
    }

    @Test
    public void constructor_initFalseTempFalse_doesNothing() {
        var value = new AtomicBoolean(false);

        try (var ignored = new TempValue<>(value.get(), false, value::set)) {
            assertFalse(value.get());
        }

        assertFalse(value.get());
    }

    @Test
    public void constructor_initFalseTempTrueNested_doesNotOverride() {
        var value = new AtomicBoolean(false);

        try (var ignored = new TempValue<>(value.get(), true, value::set)) {
            assertTrue(value.get());

            try (var ignoredNested = new TempValue<>(value.get(), true, value::set)) {
                assertTrue(value.get());
            }

            assertTrue(value.get());
        }

        assertFalse(value.get());
    }
}
