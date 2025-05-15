package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.BeanFactory;

public class MainFooter extends GridPane {
    @FXML
    private BitPointerRow wRegisterRow;

    @FXML
    private BitPointerRow programCounterRow;

    public MainFooter() {
        FxUtils.loadHierarchy(this, "components/mainFooter.fxml");
    }

    public void initialize(BeanFactory beanFactory) {
        wRegisterRow.setData(beanFactory.getBean(WRegister.class));
        programCounterRow.setData(beanFactory.getBean(ProgramCounter.class));
    }

    public void resetChanged() {
        wRegisterRow.resetChanged();
        programCounterRow.resetChanged();
    }
}
