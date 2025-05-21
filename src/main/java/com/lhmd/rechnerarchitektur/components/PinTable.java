package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.pins.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.BeanFactory;

import java.util.List;

public class PinTable extends ScrollPane {
    @FXML
    private VBox raPinsVBox;

    @FXML
    private VBox rbPinsVBox;

    private PinManager pinManager;

    public PinTable() {
        FxUtils.loadHierarchy(this, "components/pinTable.fxml");
    }

    public void initialize(BeanFactory beanFactory) {
        pinManager = beanFactory.getBean(PinManager.class);

        initializePinRows(PinType.A);
        initializePinRows(PinType.B);
    }

    public void resetChanged() {
        resetChanged(PinType.A);
        resetChanged(PinType.B);
    }

    private void initializePinRows(PinType type) {
        var pins = pinManager.getPins(type);
        var pinRows = getPinRows(type);

        for (var i = 0; i < pinRows.size(); i++) {
            var pinRow = (PinRow) pinRows.get(i);
            pinRow.setData(pins.get(i));
        }
    }

    private void resetChanged(PinType type) {
        for (var node : getPinRows(type)) {
            var pinRow = (PinRow) node;
            pinRow.resetChanged();
        }
    }

    private List<Node> getPinRows(PinType type) {
        return (type == PinType.A ? raPinsVBox : rbPinsVBox).getChildren();
    }
}
