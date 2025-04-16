module com.lhmd.rechnerarchitektur {
    requires javafx.fxml;
    requires atlantafx.base;
    requires org.girod.javafx.svgimage;

//	requires org.controlsfx.controls;
//	requires com.dlsc.formsfx;

    opens com.lhmd.rechnerarchitektur to javafx.fxml;
    exports com.lhmd.rechnerarchitektur;
    exports com.lhmd.rechnerarchitektur.instructions;
    exports com.lhmd.rechnerarchitektur.common;
    opens com.lhmd.rechnerarchitektur.instructions to javafx.fxml;
    exports com.lhmd.rechnerarchitektur.themes;
    opens com.lhmd.rechnerarchitektur.themes to javafx.fxml;
    exports com.lhmd.rechnerarchitektur.registers;
    opens com.lhmd.rechnerarchitektur.registers to javafx.fxml;
    exports com.lhmd.rechnerarchitektur.memory;
    opens com.lhmd.rechnerarchitektur.memory to javafx.fxml;
    exports com.lhmd.rechnerarchitektur.changes;
    exports com.lhmd.rechnerarchitektur.values;
    exports com.lhmd.rechnerarchitektur.parsing;
    opens com.lhmd.rechnerarchitektur.parsing to javafx.fxml;
}
