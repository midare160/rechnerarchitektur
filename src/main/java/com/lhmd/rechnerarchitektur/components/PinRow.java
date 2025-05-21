package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.pins.*;
import com.lhmd.rechnerarchitektur.styles.PseudoClasses;
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
    private Label valueLabel;

    private final String name;

    private Pin pin;
    private PinDirection previousDirection;
    private boolean previousState;
    private boolean previousValue;

    public PinRow(@NamedArg("name") String name) {
        this.name = name;

        FxUtils.loadHierarchy(this, "components/pinRow.fxml");
    }

    public void setData(Pin pin) {
        this.pin = pin;

        updateLabels();
        pin.onDirectionChanged().addListener((o, n) -> updateLabels());
        pin.onStateChanged().addListener((o, n) -> updateLabels());
        pin.onValueChanged().addListener((o, n) -> updateLabels());

        directionLabel.setOnMouseClicked(e -> pin.toggleDirection());
        stateLabel.setOnMouseClicked(e -> pin.toggleState());
    }

    public void resetChanged() {
        previousDirection = pin.getDirection();
        previousState = pin.getState();
        previousValue = pin.getValue();

        updateLabels();
    }

    private void updateLabels() {
        nameLabel.setText(name);

        directionLabel.setText(pin.getDirection() == PinDirection.OUT ? "OUT" : "IN");
        directionLabel.pseudoClassStateChanged(PseudoClasses.CHANGED, pin.getDirection() != previousDirection);

        stateLabel.setText(pin.getState() ? "1" : "0");
        stateLabel.pseudoClassStateChanged(PseudoClasses.CHANGED, pin.getState() != previousState);

        valueLabel.setText(pin.getValue() ? "1" : "0");
        valueLabel.pseudoClassStateChanged(PseudoClasses.CHANGED, pin.getValue() != previousValue);
    }
}
