module com.lhmd.rechnerarchitektur {
    requires javafx.fxml;
    requires atlantafx.base;
    requires org.girod.javafx.svgimage;

//	requires org.controlsfx.controls;
//	requires com.dlsc.formsfx;

    opens com.lhmd.rechnerarchitektur to javafx.fxml;
    exports com.lhmd.rechnerarchitektur;
    exports com.lhmd.rechnerarchitektur.instructions;
    opens com.lhmd.rechnerarchitektur.instructions to javafx.fxml;
    exports com.lhmd.rechnerarchitektur.themes;
    opens com.lhmd.rechnerarchitektur.themes to javafx.fxml;
}
