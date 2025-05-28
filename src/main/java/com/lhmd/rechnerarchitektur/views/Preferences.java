package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.components.common.NumberField;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.styles.ThemeManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private ComboBox<String> themeComboBox;

    @FXML
    private NumberField clockField;

    @FXML
    private NumberField executionIntervalField;

    @FXML
    private CheckBox watchdogCheckBox;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private final UserConfig userConfig;
    private final ThemeManager themeManager;

    public Preferences(UserConfigService userConfigService, ThemeManager themeManager) {
        this.userConfig = userConfigService.config();
        this.themeManager = themeManager;
    }

    @FXML
    private void initialize() {
        initializeGridPane();

        themeComboBox.setItems(FXCollections.observableArrayList(themeManager.themes().keySet()));
        themeComboBox.setValue(themeManager.getCurrentThemeName());

        clockField.setText(String.valueOf(userConfig.getClock()));
        clockField.textProperty().addListener((ob, o, n) -> setSaveButtonDisabled(n));

        executionIntervalField.setText(String.valueOf(userConfig.getExecutionInterval()));
        executionIntervalField.textProperty().addListener((ob, o, n) -> setSaveButtonDisabled(n));

        watchdogCheckBox.setSelected(userConfig.isWatchdogEnabled());

        okButton.setOnAction(e -> save());
        cancelButton.setOnAction(e -> FxUtils.closeWindow(root.getScene().getWindow()));
    }

    private void initializeGridPane() {
        for (var i = 0; i < root.getRowCount(); i++) {
            root.getRowConstraints().add(new RowConstraints(35));
        }
    }

    private void save() {
        themeManager.setCurrentThemeName(themeComboBox.getValue());
        userConfig.setClock(Double.parseDouble(clockField.getText()));
        userConfig.setExecutionInterval(Long.parseLong(executionIntervalField.getText()));
        userConfig.setWatchdogEnabled(watchdogCheckBox.isSelected());

        FxUtils.closeWindow(root.getScene().getWindow());
    }

    private void setSaveButtonDisabled(String numberInput) {
        var invalid = StringUtils.isNullOrWhitespace(numberInput) || Double.parseDouble(numberInput) <= 0;
        okButton.setDisable(invalid);
    }
}
