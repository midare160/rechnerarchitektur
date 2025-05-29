package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.JavaFxApplication;
import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.components.common.BitPointerRow;
import com.lhmd.rechnerarchitektur.time.RuntimeManager;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.time.Watchdog;
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
    private Label watchdogLabel;

    @FXML
    private Button resetRuntimeButton;

    @FXML
    private BitPointerRow programCounterRow;

    private RuntimeManager runtimeManager;
    private Watchdog watchdog;

    public MainFooter() {
        FxUtils.loadHierarchy(this, "components/mainFooter.fxml");
    }

    public void initialize(BeanFactory beanFactory) {
        runtimeManager = beanFactory.getBean(RuntimeManager.class);
        watchdog = beanFactory.getBean(Watchdog.class);

        wRegisterRow.setData(beanFactory.getBean(WRegister.class));
        programCounterRow.setData(beanFactory.getBean(ProgramCounter.class));

        updateTimeLabels();
        runtimeManager.runtime().onChanged().addListener((o, n) -> updateTimeLabels());
        watchdog.timer().onChanged().addListener((o, n) -> updateTimeLabels());

        resetRuntimeButton.setGraphic(SVGLoader.load(JavaFxApplication.class.getResource("svgs/reset.svg")));
        resetRuntimeButton.setOnAction(e -> runtimeManager.reset());
    }

    public void resetChanged() {
        wRegisterRow.resetChanged();
        programCounterRow.resetChanged();
    }

    private void updateTimeLabels() {
        var runtimeText = "Elapsed: %.2f µs".formatted(runtimeManager.runtime().get());
        var watchdogText = "WDT: %d µs".formatted(watchdog.timer().get());

        Platform.runLater(() -> {
            runtimeLabel.setText(runtimeText);
            watchdogLabel.setText(watchdogText);
        });
    }
}
