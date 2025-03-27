module com.lhmd.rechnerarchitektur {
	requires javafx.fxml;
	requires atlantafx.base;

//	requires org.controlsfx.controls;
//	requires com.dlsc.formsfx;

	opens com.lhmd.rechnerarchitektur to javafx.fxml;
	exports com.lhmd.rechnerarchitektur;
	exports com.lhmd.rechnerarchitektur.Instructions;
	opens com.lhmd.rechnerarchitektur.Instructions to javafx.fxml;
}
