package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.configuration.UserConfig;
import com.lhmd.rechnerarchitektur.configuration.UserConfigService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Lazy
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Preferences {
    @FXML
    private GridPane root;

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    private final UserConfig userConfig;

    public Preferences(UserConfigService userConfigService) {
        this.userConfig = userConfigService.config();
    }

    @FXML
    private void initialize(){
        closeButton.setOnAction(e -> FxUtils.closeWindow(root.getScene().getWindow()));
    }
}
