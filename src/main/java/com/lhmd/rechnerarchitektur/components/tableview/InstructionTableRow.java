package com.lhmd.rechnerarchitektur.components.tableview;

import com.lhmd.rechnerarchitektur.styles.PseudoClasses;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import javafx.scene.control.TableRow;

public class InstructionTableRow extends TableRow<InstructionRowModel> {
    @Override
    protected void updateItem(InstructionRowModel instruction, boolean empty) {
        super.updateItem(instruction, empty);

        var isValidRow = !empty && instruction != null;

        pseudoClassStateChanged(PseudoClasses.BREAKPOINT_ACTIVE, isValidRow && instruction.isBreakpointActiveProperty().get());
        pseudoClassStateChanged(PseudoClasses.NEXT, isValidRow && instruction.isNextProperty().get());
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);

        var isValidRow = i >= 0 && getItem() != null;

        pseudoClassStateChanged(PseudoClasses.BREAKPOINT_ACTIVE, isValidRow && getItem().isBreakpointActiveProperty().get());
        pseudoClassStateChanged(PseudoClasses.NEXT, isValidRow && getItem().isNextProperty().get());
    }
}
