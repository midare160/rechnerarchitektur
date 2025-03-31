package com.lhmd.rechnerarchitektur;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.function.BiConsumer;

public class BreakpointCell<S> extends SvgImageCell<S> {
    private static final URL breakpointEnabledSvgUrl = Launcher.class.getResource("svgs/breakpoint-enabled.svg");
    private static final URL breakpointDisabledSvgUrl = Launcher.class.getResource("svgs/breakpoint-disabled.svg");
    private static final URL disableBreakpointSvgUrl = Launcher.class.getResource("svgs/disable-breakpoint.svg");

    private final BiConsumer<S, URL> setUrl;

    public BreakpointCell(BiConsumer<S, URL> setUrl) {
        this.setUrl = setUrl;

        setOnMouseClicked(this::onMouseClicked);
        setOnMouseEntered(this::onMouseEntered);
        setOnMouseExited(this::onMouseExited);
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        var newSvgUrl = breakpointDisabledSvgUrl;

        if (getItem() == null || getItem() == breakpointDisabledSvgUrl) {
            newSvgUrl = disableBreakpointSvgUrl;
        }

        setUrl.accept(getTableRow().getItem(), newSvgUrl);
    }

    private void onMouseEntered(MouseEvent mouseEvent) {
        var newSvgUrl = disableBreakpointSvgUrl;

        if (getItem() == null) {
            newSvgUrl = breakpointDisabledSvgUrl;
        }

        setUrl.accept(getTableRow().getItem(), newSvgUrl);
        getScene().setCursor(Cursor.HAND);
    }

    private void onMouseExited(MouseEvent mouseEvent) {
        URL newSvgUrl = null;

        if (getItem() == disableBreakpointSvgUrl) {
            newSvgUrl = breakpointEnabledSvgUrl;
        }

        setUrl.accept(getTableRow().getItem(), newSvgUrl);
        getScene().setCursor(Cursor.DEFAULT);
    }
}
