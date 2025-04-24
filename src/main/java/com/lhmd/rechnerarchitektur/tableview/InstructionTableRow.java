package com.lhmd.rechnerarchitektur.tableview;

import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import javafx.css.PseudoClass;
import javafx.scene.control.TableRow;

public class InstructionTableRow extends TableRow<InstructionRowModel> {
    private static final PseudoClass BREAKPOINT_ACTIVE = PseudoClass.getPseudoClass("breakpoint-active");
    private static final PseudoClass NEXT = PseudoClass.getPseudoClass("next");

    @Override
    protected void updateItem(InstructionRowModel instruction, boolean empty) {
        super.updateItem(instruction, empty);

        var isValidRow = !empty && instruction != null;

        pseudoClassStateChanged(BREAKPOINT_ACTIVE, isValidRow && instruction.isBreakpointActiveProperty().get());
        pseudoClassStateChanged(NEXT, isValidRow && instruction.isNextProperty().get());
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);

        var isValidRow = i >= 0 && getItem() != null;

        pseudoClassStateChanged(BREAKPOINT_ACTIVE, isValidRow && getItem().isBreakpointActiveProperty().get());
        pseudoClassStateChanged(NEXT, isValidRow && getItem().isNextProperty().get());
    }
}
