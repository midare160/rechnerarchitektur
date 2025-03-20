module com.lhmd.rechnerarchitektur {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;

	opens com.lhmd.rechnerarchitektur to javafx.fxml;
	exports com.lhmd.rechnerarchitektur;
}
