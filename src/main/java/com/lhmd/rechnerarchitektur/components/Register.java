package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Register extends HBox {
    private final IntBox intBox;
    private final String name;

    public Register(IntBox intBox, int numberOfBits, String name) {
        this.intBox = intBox;
        this.name = name;

        setMaxWidth(Double.MAX_VALUE);

        var nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-family: 'Consolas'");
        addLabel(nameLabel);

        for (var i = numberOfBits - 1; i >= 0; i--) {
            addLabel(new BitPointerLabel(intBox, i));
        }
    }

    private void addLabel(Label label) {
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);

        getChildren().add(label);
    }
}
