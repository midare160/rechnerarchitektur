package com.lhmd.rechnerarchitektur;

import javafx.scene.control.TableCell;
import org.girod.javafx.svgimage.SVGLoader;

import java.net.URL;

public class SvgImageCell<S> extends TableCell<S, URL> {
    @Override
    protected void updateItem(URL item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (item == null || empty) {
            setGraphic(null);
            return;
        }

        setGraphic(SVGLoader.load(item));
    }
}
