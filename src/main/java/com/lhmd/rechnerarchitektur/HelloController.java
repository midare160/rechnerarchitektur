package com.lhmd.rechnerarchitektur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
	@FXML
	private Label welcomeText;

	@FXML
	protected void onHelloButtonClick() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
		Stage stage = new Stage();
		stage.setTitle("PIC Simulator");
		stage.setScene(scene);
		stage.show();
	}
}
