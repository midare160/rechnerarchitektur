package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.instructions.Instruction;
import javafx.scene.control.TableRow;

public class BreakpointRow extends TableRow<Instruction> {

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);

        if (i < 0 || getItem() == null || !getItem().isBreakpointActive()) {
            setStyle("");
            return;
        }

        setStyle("-fx-background-color: #40252b;");
    }
}
