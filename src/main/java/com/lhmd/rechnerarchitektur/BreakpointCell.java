package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class BreakpointCell extends SvgImageCell<InstructionViewModel> {
    private static final URL breakpointEnabledSvgUrl = Launcher.class.getResource("svgs/breakpoint-enabled.svg");
    private static final URL breakpointDisabledSvgUrl = Launcher.class.getResource("svgs/breakpoint-disabled.svg");
    private static final URL disableBreakpointSvgUrl = Launcher.class.getResource("svgs/disable-breakpoint.svg");

    public BreakpointCell() {
        setOnMouseClicked(this::onMouseClicked);
        setOnMouseEntered(this::onMouseEntered);
        setOnMouseExited(this::onMouseExited);
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        var isBreakpointActive = false;
        var newSvgUrl = breakpointDisabledSvgUrl;

        if (getItem() == null || getItem() == breakpointDisabledSvgUrl) {
            isBreakpointActive = true;
            newSvgUrl = disableBreakpointSvgUrl;
        }

        getTableRow().getItem().setIsBreakpointActive(isBreakpointActive);
        setBreakpointSvgUrl(newSvgUrl);
    }

    private void onMouseEntered(MouseEvent mouseEvent) {
        var newSvgUrl = disableBreakpointSvgUrl;

        if (getItem() == null) {
            newSvgUrl = breakpointDisabledSvgUrl;
        }

        setBreakpointSvgUrl(newSvgUrl);
        getScene().setCursor(Cursor.HAND);
    }

    private void onMouseExited(MouseEvent mouseEvent) {
        URL newSvgUrl = null;

        if (getItem() == disableBreakpointSvgUrl) {
            newSvgUrl = breakpointEnabledSvgUrl;
        }

        setBreakpointSvgUrl(newSvgUrl);
        getScene().setCursor(Cursor.DEFAULT);
    }

    private void setBreakpointSvgUrl(URL url) {
        getTableRow().getItem().setBreakpointSvgUrl(url);
    }
}
