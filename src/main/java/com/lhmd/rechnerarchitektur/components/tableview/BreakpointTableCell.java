package com.lhmd.rechnerarchitektur.components.tableview;

import com.lhmd.rechnerarchitektur.JavaFxApplication;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class BreakpointTableCell extends SvgTableCell<InstructionRowModel> {
    private static final URL BREAKPOINT_ENABLED_SVG_URL = JavaFxApplication.class.getResource("svgs/breakpoint-enabled.svg");
    private static final URL BREAKPOINT_DISABLED_SVG_URL = JavaFxApplication.class.getResource("svgs/breakpoint-disabled.svg");
    private static final URL DISABLE_BREAKPOINT_SVG_URL = JavaFxApplication.class.getResource("svgs/disable-breakpoint.svg");

    public BreakpointTableCell() {
        setOnMouseClicked(this::onMouseClicked);
        setOnMouseEntered(this::onMouseEntered);
        setOnMouseExited(this::onMouseExited);
    }

    @Override
    protected void updateItem(URL item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item != null) {
            return;
        }

        // Display the current line number if no breakpoint svg is active
        var lineNumber = getTableRow().getItem().getLineNumber();
        setText(Integer.toString(lineNumber));
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        if (isCommentRow()) {
            return;
        }

        var isBreakpointActive = false;
        var newSvgUrl = BREAKPOINT_DISABLED_SVG_URL;

        if (getItem() == null || getItem() == BREAKPOINT_DISABLED_SVG_URL) {
            isBreakpointActive = true;
            newSvgUrl = DISABLE_BREAKPOINT_SVG_URL;
        }

        getTableRow().getItem().isBreakpointActiveProperty().set(isBreakpointActive);
        setBreakpointSvgUrl(newSvgUrl);
    }

    private void onMouseEntered(MouseEvent mouseEvent) {
        if (isCommentRow()) {
            return;
        }

        var newSvgUrl = DISABLE_BREAKPOINT_SVG_URL;

        if (getItem() == null) {
            newSvgUrl = BREAKPOINT_DISABLED_SVG_URL;
        }

        setBreakpointSvgUrl(newSvgUrl);
        getScene().setCursor(Cursor.HAND);
    }

    private void onMouseExited(MouseEvent mouseEvent) {
        if (isCommentRow()) {
            return;
        }

        URL newSvgUrl = null;

        if (getItem() == DISABLE_BREAKPOINT_SVG_URL) {
            newSvgUrl = BREAKPOINT_ENABLED_SVG_URL;
        }

        setBreakpointSvgUrl(newSvgUrl);
        getScene().setCursor(Cursor.DEFAULT);
    }

    private boolean isCommentRow() {
        return getTableRow() == null
                || getTableRow().getItem() == null
                || !getTableRow().getItem().isExecutable();
    }

    private void setBreakpointSvgUrl(URL url) {
        getTableRow().getItem().breakpointSvgUrlProperty().set(url);
    }
}
