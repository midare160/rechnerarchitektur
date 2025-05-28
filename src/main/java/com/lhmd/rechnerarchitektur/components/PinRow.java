package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.components.common.Led;
import com.lhmd.rechnerarchitektur.pins.*;
import com.lhmd.rechnerarchitektur.styles.PseudoClasses;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PinRow extends HBox {
    @FXML
    private Label nameLabel;

    @FXML
    private Label directionLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Led valueLed;

    private final String name;

    private Pin pin;
    private PinDirection previousDirection;
    private boolean previousState;

    public PinRow(@NamedArg("name") String name) {
        this.name = name;

        FxUtils.loadHierarchy(this, "components/pinRow.fxml");
        nameLabel.setText(name);
    }

    public void setData(Pin pin) {
        this.pin = pin;

        updateControls();
        pin.onDirectionChanged().addListener((o, n) -> updateControls());
        pin.onStateChanged().addListener((o, n) -> updateControls());
        pin.onValueChanged().addListener((o, n) -> updateControls());

        directionLabel.setOnMouseClicked(e -> pin.toggleDirection());
        stateLabel.setOnMouseClicked(e -> pin.toggleState());
        valueLed.setOnMouseClicked(e -> pin.toggleValue());
    }

    public void resetChanged() {
        previousDirection = pin.getDirection();
        previousState = pin.getState();

        updateControls();
    }

    private void updateControls() {
        Platform.runLater(() -> {
            directionLabel.setText(pin.getDirection() == PinDirection.OUT ? "OUT" : "IN");
            directionLabel.pseudoClassStateChanged(PseudoClasses.CHANGED, pin.getDirection() != previousDirection);

            stateLabel.setText(pin.getState() ? "HIGH" : "LOW");
            stateLabel.pseudoClassStateChanged(PseudoClasses.CHANGED, pin.getState() != previousState);

            valueLed.setActive(pin.getValue());
        });
    }
}
