package com.lhmd.rechnerarchitektur.styles;

import javafx.css.PseudoClass;

public class PseudoClasses {
    private PseudoClasses() {
    }

    public static final PseudoClass CHANGED = PseudoClass.getPseudoClass("changed");
    public static final PseudoClass BREAKPOINT_ACTIVE = PseudoClass.getPseudoClass("breakpoint-active");
    public static final PseudoClass NEXT = PseudoClass.getPseudoClass("next");
}
