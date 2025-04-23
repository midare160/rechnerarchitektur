package com.lhmd.rechnerarchitektur.controllers;

import javafx.beans.property.IntegerProperty;

public class RegisterController {
    private final int address;
    private final IntegerProperty register;

    public RegisterController(int address, IntegerProperty register) {
        this.address = address;
        this.register = register;
    }
}
