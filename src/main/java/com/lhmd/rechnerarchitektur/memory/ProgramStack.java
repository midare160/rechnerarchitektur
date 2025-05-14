package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.events.ResetEvent;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgramStack {
    public static final int MAX_SIZE = 8;
    public static final int ELEMENT_WIDTH = 13;

    private final IntBox pointer;
    private final IntBox[] elements;

    public ProgramStack() {
        pointer = new IntBox();
        pointer.onChanged().addListener(this::onPointerChanged);

        elements = new IntBox[MAX_SIZE];

        for (var i = 0; i < elements.length; i++) {
            elements[i] = new IntBox();
        }
    }

    @EventListener(ResetEvent.class)
    public void handleReset() {
        pointer.set(0);

        for (var element : elements) {
            element.set(0);
        }
    }

    public IntBox pointer() {
        return pointer;
    }

    public List<IntBox> elements() {
        return List.of(elements);
    }

    public void push(int value) {
        elements[getPointer(0)].set(value);
        pointer.set(getPointer(1));
    }

    public int pop() {
        pointer.set(getPointer(-1));

        return elements[getPointer(0)].get();
    }

    public int peek() {
        return elements[getPointer(-1)].get();
    }

    private void onPointerChanged(Integer oldValue, Integer newValue) {
        var modPointer = getPointer(0);

        if (newValue != modPointer) {
            pointer.set(modPointer);
        }
    }

    private int getPointer(int offset) {
        // Negative numbers behave incorrectly with %
        return Math.floorMod(pointer.get() + offset, MAX_SIZE);
    }
}
