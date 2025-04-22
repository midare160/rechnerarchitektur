package com.lhmd.rechnerarchitektur.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RegisterController {
    private final int address;
    private final IntegerProperty register;

    @FXML
    private HBox bitsContainer;
    private final Label[] bitLabels = new Label[8];

    public RegisterController(int address, IntegerProperty register) {
        this.address = address;
        this.register = register;
    }
}
