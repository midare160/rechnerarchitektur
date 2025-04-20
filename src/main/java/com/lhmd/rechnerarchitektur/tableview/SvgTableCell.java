package com.lhmd.rechnerarchitektur.tableview;

import javafx.scene.control.TableCell;
import org.girod.javafx.svgimage.SVGLoader;

import java.net.URL;

public class SvgTableCell<S> extends TableCell<S, URL> {
    @Override
    protected void updateItem(URL item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty || item == null) {
            setGraphic(null);
            return;
        }

        setGraphic(SVGLoader.load(item));
    }
}
