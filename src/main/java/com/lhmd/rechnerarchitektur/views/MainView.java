package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainView {

    private ObservableList<InstructionViewModel> instructions;

    @FXML
    private TableView<InstructionViewModel> instructionsTableView;

    public void initialize(){

    }
}
