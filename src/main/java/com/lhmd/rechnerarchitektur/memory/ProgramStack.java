package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgramStack {
    public static final int MAX_SIZE = 8;
    public static final int ELEMENT_WIDTH = 13;

    private final IntBox pointer;
    private final List<IntBox> elements;

    public ProgramStack() {
        this.pointer = new IntBox();
        this.pointer.onChanged().addListener(this::onPointerChanged);

        this.elements = createInitialElements();
    }

    public IntBox pointer() {
        return pointer;
    }

    public List<IntBox> elements() {
        return elements;
    }

    public void push(int value) {
        elements.get(getPointer(0)).set(value);
        pointer.set(getPointer(1));
    }

    public int pop() {
        pointer.set(getPointer(-1));

        return elements.get(getPointer(0)).get();
    }

    public int peek() {
        return elements.get(getPointer(-1)).get();
    }

    private List<IntBox> createInitialElements() {
        var elementArray = new IntBox[MAX_SIZE];

        for (var i = 0; i < elementArray.length; i++) {
            elementArray[i] = new IntBox();
        }

        return List.of(elementArray);
    }

    private int getPointer(int offset) {
        return Math.floorMod(pointer.get() + offset, MAX_SIZE);
    }

    private void onPointerChanged(Integer oldValue, Integer newValue) {
        var modPointer = getPointer(0);

        if (newValue != modPointer) {
            pointer.set(modPointer);
        }
    }
}
