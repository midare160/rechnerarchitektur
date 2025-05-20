package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
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

    private final int pinIndex;
    private final String pinName;

    public PinRow(@NamedArg("pinIndex") int pinIndex, String pinName) {
        this.pinIndex = pinIndex;
        this.pinName = pinName;

        FxUtils.loadHierarchy(this, "/components/pinRow.fxml");
    }
}
