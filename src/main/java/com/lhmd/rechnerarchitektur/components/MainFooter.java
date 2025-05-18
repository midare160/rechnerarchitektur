package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.computing.CycleManager;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.registers.WRegister;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.BeanFactory;

public class MainFooter extends GridPane {
    @FXML
    private BitPointerRow wRegisterRow;

    @FXML
    private Label runtimeLabel;

    @FXML
    private BitPointerRow programCounterRow;

    private CycleManager cycleManager;

    public MainFooter() {
        FxUtils.loadHierarchy(this, "components/mainFooter.fxml");
    }

    public void initialize(BeanFactory beanFactory) {
        wRegisterRow.setData(beanFactory.getBean(WRegister.class));
        programCounterRow.setData(beanFactory.getBean(ProgramCounter.class));
        cycleManager = beanFactory.getBean(CycleManager.class);

        updateRuntimeLabel();
        cycleManager.onRuntimeChanged().addListener((o, n) -> updateRuntimeLabel());
    }

    public void resetChanged() {
        wRegisterRow.resetChanged();
        programCounterRow.resetChanged();
    }

    private void updateRuntimeLabel(){
        var text = "Elapsed: %.2f µs".formatted(cycleManager.getRuntime());

        Platform.runLater(() -> runtimeLabel.setText(text));
    }
}
