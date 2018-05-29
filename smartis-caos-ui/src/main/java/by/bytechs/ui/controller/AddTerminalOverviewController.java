package by.bytechs.ui.controller;

import by.bytechs.ui.config.javaFxConfig.FXMLController;
import by.bytechs.ui.view.AddTerminalView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Romanovich Andrei
 */
@FXMLController
public class AddTerminalOverviewController {
    @FXML
    private ComboBox<String> logicalNameComboBox;
    @FXML
    private TextField logicalNameTextField;
    @FXML
    private TextField terminalId;
    @FXML
    private TextField ipAddressTextField;
    @FXML
    private TextField portNumberTextField;
    @FXML
    private TextField modelTextField;
    @FXML
    private TextField cbuTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField areaTextField;
    @FXML
    private TextField rigeonTextField;
    @FXML
    private TextField latitudeTextField;
    @FXML
    private TextField longitudeTextField;

    @Autowired
    private AddTerminalView addTerminalView;

    @FXML
    public void initialize() {
        ObservableList<String> logicalNameObservableList = FXCollections.observableArrayList("ДМ");
        logicalNameComboBox.setItems(logicalNameObservableList);
        logicalNameComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void addTerminalOnAction() {

    }

    @FXML
    public void closeView() {
        addTerminalView.closeView();
    }

    public void update() {
        logicalNameComboBox.getSelectionModel().select(0);
    }
}