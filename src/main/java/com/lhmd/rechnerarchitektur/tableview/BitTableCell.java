package com.lhmd.rechnerarchitektur.tableview;

import com.lhmd.rechnerarchitektur.PseudoClasses;
import com.lhmd.rechnerarchitektur.properties.BitPointerProperty;
import javafx.scene.input.*;

public class BitTableCell<S> extends FormattedTableCell<S, Boolean> {
    public BitTableCell() {
        super(v -> v ? "1" : "0");

        setOnMouseClicked(this::onMouseClicked);
    }

    public void resetChanged() {
        var property = getProperty();

        if (property == null) {
            return;
        }

        property.resetChanged();
        updatePseudoClass(false);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            return;
        }

        updatePseudoClass(getProperty().isChanged());
    }

    private void onMouseClicked(MouseEvent e) {
        var property = getProperty();
        property.set(!property.get());
    }

    private void updatePseudoClass(boolean isChanged) {
        pseudoClassStateChanged(PseudoClasses.CHANGED, isChanged);
    }

    private BitPointerProperty getProperty() {
        return (BitPointerProperty) getTableColumn()
                .getCellObservableValue(getIndex());
    }
}
