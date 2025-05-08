package com.lhmd.rechnerarchitektur.events;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.*;

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
    public void addListener_multipleListeners_insertedInCorrectOrder() {
        var changedEvent = new ChangedEvent<Double>();

        ChangeListener<Double> listener1 = (o, n) -> {};
        ChangeListener<Double> listener2 = (o, n) -> {};

        changedEvent.addListener(listener1);
        changedEvent.addListener(listener2);

        var iterator = changedEvent.getListeners().iterator();
        assertEquals(listener2, iterator.next());
        assertEquals(listener1, iterator.next());
    }

    @Test
    public void removeListener_sameListenerAddedMultipleTimes_removesLastOccurrence() {
        var changedEvent = new ChangedEvent<Long>();

        ChangeListener<Long> listener1 = (o, n) -> {};
        ChangeListener<Long> listener2 = (o, n) -> {};

        changedEvent.addListener(listener1);
        changedEvent.addListener(listener1);
        changedEvent.addListener(listener2);
        changedEvent.addListener(listener1);

        changedEvent.removeListener(listener1);

        var iterator = changedEvent.getListeners().iterator();
        assertEquals(listener2, iterator.next());
        assertEquals(listener1, iterator.next());
        assertEquals(listener1, iterator.next());
    }

    @Test
    public void fire_changedValue_oldAndNewValueCorrect() {
        var hasRun = new AtomicBoolean();

        var changedEvent = new ChangedEvent<Float>();
        changedEvent.addListener((o, n) -> {
            assertNotEquals(o, n);
            hasRun.set(true);
        });

        changedEvent.fire(4.2f, 6.9f);

        assertTrue(hasRun.get());
    }

    @Test
    public void fire_unchangedValue_doesntExecuteListeners() {
        var changedEvent = new ChangedEvent<Boolean>();
        changedEvent.addListener((o, n) -> fail("Listener should not be executed"));

        changedEvent.fire(true, true);
    }

    @Test
    public void fire_lambda_oldAndNewValueCorrect() {
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
