package com.lhmd.rechnerarchitektur.changes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ChangedEventTest {

    @Test
    public void addListener_getsAddedAndRemoved() {
        var changedEvent = new ChangedEvent<Integer>();
        ChangeListener<Integer> listener = (o, n) -> {};

        changedEvent.addListener(listener);
        assertEquals(1, changedEvent.getListeners().size());

        changedEvent.removeListener(listener);
        assertEquals(0, changedEvent.getListeners().size());
    }

    @Test
    public void fire_unchangedValue_doesExecuteListeners() {
        var changedEvent = new ChangedEvent<Integer>();
        changedEvent.addListener(Assertions::assertNotEquals);

        changedEvent.fire(5, 5);
    }

    @Test
    public void fireLambda_oldAndNewValueCorrect() {
        var changedEvent = new ChangedEvent<Integer>();
        var field = new AtomicInteger(5);
        var hasRun = new AtomicBoolean();

        changedEvent.addListener((o, n) -> {
            assertEquals(5, o);
            assertEquals(10, n);

            hasRun.set(true);
        });

        changedEvent.fire(field::get, () -> field.set(10));
        assertEquals(10, field.get());
        assertTrue(hasRun.get());
    }
}
