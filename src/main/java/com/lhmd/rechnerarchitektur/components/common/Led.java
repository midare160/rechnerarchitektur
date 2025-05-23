package com.lhmd.rechnerarchitektur.components.common;

import javafx.beans.property.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;

public class Led extends Circle {
    private final Paint onFill;
    private final Paint offFill;
    private final BooleanProperty active;

    public Led() {
        setRadius(8);

        this.onFill = createOnFill();
        this.offFill = createOffFill();

        this.active = new SimpleBooleanProperty();
        this.active.addListener((ob, o, n) -> updateFill());

        updateFill();
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean value) {
        active.set(value);
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    private RadialGradient createOnFill() {
        return new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(1, Color.RED));
    }

    private RadialGradient createOffFill() {
        return new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.GRAY),
                new Stop(1, Color.DARKRED));
    }

    private void updateFill() {
        setFill(active.get() ? onFill : offFill);
    }
}
