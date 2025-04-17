package com.lhmd.rechnerarchitektur.tableview;

import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import javafx.css.PseudoClass;
import javafx.scene.control.TableRow;

public class BreakpointRow extends TableRow<InstructionViewModel> {

    private static final PseudoClass BREAKPOINT_ACTIVE = PseudoClass.getPseudoClass("breakpoint-active");

    @Override
    protected void updateItem(InstructionViewModel instruction, boolean empty) {
        super.updateItem(instruction, empty);

        var isActive = !empty && instruction != null && instruction.isBreakpointActiveProperty().get();

        pseudoClassStateChanged(BREAKPOINT_ACTIVE, isActive);
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);

        var isActive = i >= 0 && getItem() != null && getItem().isBreakpointActiveProperty().get();

        pseudoClassStateChanged(BREAKPOINT_ACTIVE, isActive);
    }
}
