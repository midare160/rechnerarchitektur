package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.PseudoClasses;
import com.lhmd.rechnerarchitektur.memory.ProgramStack;

public class StackTable extends BitPointerTable {
    private ProgramStack stack;

    public StackTable() {
        super(13);
    }

    public void setData(ProgramStack stack) {
        this.stack = stack;

        var elements = stack.elements();

        addRow(stack.pointer(), "Pointer");
        stack.pointer().onChanged().addListener((o, n) -> setCurrentRow());

        for (var i = 0; i < elements.size(); i++) {
            addRow(elements.get(i), "0x%02X".formatted(i));
        }

        setCurrentRow();
    }

    private void setCurrentRow() {
        var rows = getRows();
        var currentPointer = stack.pointer().get();

        // First row is the pointer row
        for (var i = 1; i < rows.size(); i++) {
            rows.get(i).pseudoClassStateChanged(PseudoClasses.NEXT, i - 1 == currentPointer);
        }
    }
}
