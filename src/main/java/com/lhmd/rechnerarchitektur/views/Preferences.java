package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.components.common.NumberField;
import com.lhmd.rechnerarchitektur.configuration.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Preferences {
    @FXML
    private GridPane root;

    @FXML
    private NumberField clockField;

    @FXML
    private NumberField executionIntervalField;

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    private final UserConfig userConfig;

    public Preferences(UserConfigService userConfigService) {
        this.userConfig = userConfigService.config();
    }

    @FXML
    private void initialize() {
        clockField.setText(Double.toString(userConfig.getClock()));
        executionIntervalField.setText(Long.toString(userConfig.getExecutionInterval()));

        closeButton.setOnAction(e -> FxUtils.closeWindow(root.getScene().getWindow()));
        saveButton.setOnAction(e -> save());
    }

    private void save() {
        userConfig.setClock(Double.parseDouble(clockField.getText()));
        userConfig.setExecutionInterval(Long.parseLong(executionIntervalField.getText()));

        FxUtils.closeWindow(root.getScene().getWindow());
    }
}
