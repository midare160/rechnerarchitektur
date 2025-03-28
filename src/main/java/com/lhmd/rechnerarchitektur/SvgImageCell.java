package com.lhmd.rechnerarchitektur;

import javafx.scene.control.TableCell;
import org.girod.javafx.svgimage.SVGImage;

public class SvgImageCell<S> extends TableCell<S, Void> {
    private SVGImage svgImage;

    public SVGImage getSvgImage() {
        return svgImage;
    }

    public void setSvgImage(SVGImage svgImage) {
        this.svgImage = svgImage;
        updateItem(null, svgImage == null);
    }

    @Override
    protected void updateItem(Void t, boolean empty) {
        super.updateItem(t, empty);

        setGraphic(svgImage);
    }
}
