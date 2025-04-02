package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.instructions.Instruction;
import javafx.css.PseudoClass;
import javafx.scene.control.TableRow;

public class BreakpointRow extends TableRow<Instruction> {

    private static final PseudoClass BREAKPOINT_ACTIVE = PseudoClass.getPseudoClass("breakpoint-active");

    @Override
    protected void updateItem(Instruction instruction, boolean empty) {
        super.updateItem(instruction, empty);

        var isActive = !empty && instruction != null && instruction.isBreakpointActive();

        pseudoClassStateChanged(BREAKPOINT_ACTIVE, isActive);
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);

        var isActive = i >= 0 && getItem() != null && getItem().isBreakpointActive();

        pseudoClassStateChanged(BREAKPOINT_ACTIVE, isActive);
    }
}
