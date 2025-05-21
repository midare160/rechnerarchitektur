package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.JavaFxApplication;
import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.time.RuntimeManager;
import com.lhmd.rechnerarchitektur.registers.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.girod.javafx.svgimage.SVGLoader;
import org.springframework.beans.factory.BeanFactory;

public class MainFooter extends GridPane {
    @FXML
    private BitPointerRow wRegisterRow;

    @FXML
    private Label runtimeLabel;

    @FXML
    private Button resetRuntimeButton;

    @FXML
    private BitPointerRow programCounterRow;

    private RuntimeManager runtimeManager;

    public MainFooter() {
        FxUtils.loadHierarchy(this, "components/mainFooter.fxml");
    }

    public void initialize(BeanFactory beanFactory) {
        wRegisterRow.setData(beanFactory.getBean(WRegister.class));
        programCounterRow.setData(beanFactory.getBean(ProgramCounter.class));
        runtimeManager = beanFactory.getBean(RuntimeManager.class);

        updateRuntimeLabel();
        runtimeManager.runtime().onChanged().addListener((o, n) -> updateRuntimeLabel());

        resetRuntimeButton.setGraphic(SVGLoader.load(JavaFxApplication.class.getResource("svgs/reset.svg")));
        resetRuntimeButton.setOnAction(e -> runtimeManager.reset());
    }

    public void resetChanged() {
        wRegisterRow.resetChanged();
        programCounterRow.resetChanged();
    }

    private void updateRuntimeLabel() {
        var text = "Elapsed: %.2f µs".formatted(runtimeManager.runtime().getValue());

        Platform.runLater(() -> runtimeLabel.setText(text));
    }
}
