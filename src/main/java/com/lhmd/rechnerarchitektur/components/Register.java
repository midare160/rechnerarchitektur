package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import javafx.scene.layout.HBox;

public class Register extends HBox {
    public Register() {
        FxUtils.loadHierarchy(this, "components/register.fxml");
    }
}
