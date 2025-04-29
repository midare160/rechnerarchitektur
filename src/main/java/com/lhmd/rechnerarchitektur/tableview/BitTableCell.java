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
        var nextActive = false;
        var property = getProperty();

        if (property != null) {
            nextActive = property.isChanged();
            property.resetChanged();
        }

        updatePseudoClass(nextActive);
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);

        var nextActive = !empty && item != null && getProperty().isChanged();
        updatePseudoClass(nextActive);
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i);

        var nextActive = i >= 0 && getItem() != null && getProperty().isChanged();
        updatePseudoClass(nextActive);
    }

    private void onMouseClicked(MouseEvent e) {
        var property = getProperty();
        property.set(!property.get());
    }

    private void updatePseudoClass(boolean active) {
//        pseudoClassStateChanged(PseudoClasses.CHANGED, active);

        if (active) {
            setStyle("-fx-background-color: red");
        } else {
            setStyle("");
        }
    }

    private BitPointerProperty getProperty() {
        return (BitPointerProperty) getTableColumn()
                .getCellObservableValue(getIndex());
    }
}
