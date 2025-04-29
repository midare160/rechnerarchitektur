package com.lhmd.rechnerarchitektur.values;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {

    @Test
    public void getValue_withInitialValue_valuesEqual() {
        var expected = 2;

        var box = new Box<>(expected);

        assertEquals(expected, box.getValue());
    }

    @Test
    public void setValue_withoutInitialValue_getsUpdated() {
        var expected = 2;

        var box = new Box<Integer>();
        assertNull(box.getValue());

        box.setValue(expected);
        assertEquals(expected, box.getValue());
    }

    @Test
    public void addListener_oldAndNewValuesCorrect() {
        var expectedOld = 4.2;
        var expectedNew = 6.9;

        var box = new Box<>(expectedOld);

        box.onChanged().addListener((o, n) -> {
            assertEquals(expectedOld, o);
            assertEquals(expectedNew, n);
        });

        box.setValue(expectedNew);
    }

    @Test
    public void addListener_getsCalled() {
        var box = new Box<>(4.2);

        var called = new AtomicBoolean();
        box.onChanged().addListener((o, n) -> called.set(true));

        box.setValue(6.9);

        assertTrue(called.get());
    }

    @Test
    public void addListener_multipleListeners_getCalled() {
        var firstCalled = new AtomicBoolean();
        var secondCalled = new AtomicBoolean();

        var box = new Box<Double>();

        box.onChanged().addListener((o, n) -> {
            assertFalse(secondCalled.get());
            firstCalled.set(true);
        });

        box.onChanged().addListener((o, n) -> secondCalled.set(true));

        box.setValue(6.9);

        assertTrue(firstCalled.get());
        assertTrue(secondCalled.get());
    }

    @Test
    public void equals_sameValues_returnTrue() {
        var box1 = new Box<>(4.2);
        var box2 = new Box<>(4.2);

        assertEquals(box1, box2);
    }

    @Test
    public void equals_differentGenericsWithSameValues_doesNotEqual() {
        var floatBox = new Box<>(4.0f);
        var doubleBox = new Box<>(4.0);

        //noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(floatBox, doubleBox);
    }

    @Test
    public void compareTo_getSortedCorrectly() {
        var box1 = new Box<>(3);
        var box2 = new Box<>(2);
        var box3 = new Box<>(1);

        var boxArray = new Box[]{box1, box2, box3};
        Arrays.sort(boxArray);

        assertArrayEquals(new Box[]{box3, box2, box1}, boxArray);
    }

    @Test
    public void compareTo_compareValueToValue_ReturnsCorrectValue() {
        assertEquals(-1, new Box<>(3).compareTo(new Box<>(4)));
        assertEquals(0, new Box<>(3).compareTo(new Box<>(3)));
        assertEquals(1, new Box<>(4).compareTo(new Box<>(3)));
    }

    @Test
    public void compareTo_compareValueToNull_ReturnsOne() {
        var box = new Box<>(3);

        assertEquals(1, box.compareTo(null));
    }

    @Test
    public void compareTo_compareNullValueToNull_ReturnsOne() {
        var box = new Box<Integer>();

        assertEquals(1, box.compareTo(null));
    }

    @Test
    public void compareTo_compareNullValueToNullValue_ReturnsZero() {
        var box1 = new Box<Integer>();
        var box2 = new Box<Integer>();

        assertEquals(0, box1.compareTo(box2));
    }

    @Test
    public void compareTo_compareNullValueToValue_ReturnsMinusOne() {
        var box1 = new Box<Integer>();
        var box2 = new Box<>(3);

        assertEquals(-1, box1.compareTo(box2));
    }
}
