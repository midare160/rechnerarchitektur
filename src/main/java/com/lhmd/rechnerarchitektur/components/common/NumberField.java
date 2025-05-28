package com.lhmd.rechnerarchitektur.components.common;

import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.*;

public class NumberField extends TextField {
    private final NumberFieldType type;

    public NumberField(@NamedArg("type") NumberFieldType type) {
        this.type = type;

        setAlignment(Pos.CENTER_RIGHT);

        setTextFormatter(new TextFormatter<>(c -> {
            var newText = c.getControlNewText();

            if (type.pattern().matcher(newText).matches()) {
                return c;
            }

            return null;
        }));
    }
}
