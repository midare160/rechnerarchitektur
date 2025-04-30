package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MainFooter extends GridPane {
    @FXML
    private BitPointerRow wRegisterRow;

    @FXML
    private BitPointerRow programCounterRow;

    public MainFooter() {
        FxUtils.loadHierarchy(this, "components/mainFooter.fxml");
    }

    public void setData(IntBox wRegister, ProgramCounter programCounter) {
        wRegisterRow.setData(wRegister);
        programCounterRow.setData(programCounter);
    }

    public void resetChanged() {
        wRegisterRow.resetChanged();
        programCounterRow.resetChanged();
    }
}
