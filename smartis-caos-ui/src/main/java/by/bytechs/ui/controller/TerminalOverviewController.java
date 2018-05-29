package by.bytechs.ui.controller;

import by.bytechs.dto.CashUnitDTO;
import by.bytechs.dto.TerminalInfoDTO;
import by.bytechs.dto.TerminalLogDTO;
import by.bytechs.ui.config.javaFxConfig.FXMLController;
import by.bytechs.ui.service.interfaces.TerminalService;
import by.bytechs.util.constraints.DeviceStatusConstraints;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Kotsuba_VV on 23.02.2017.
 */
@FXMLController
public class TerminalOverviewController {
    @Autowired
    private TerminalService terminalService;
    @FXML
    private TableView<TerminalInfoDTO> terminalTable;
    @FXML
    private TableColumn<TerminalInfoDTO, String> terminalIDColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, String> logicalNameColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, String> hostColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, Integer> portColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, String> addressColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, ImageView> overallStatusColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, ImageView> networkColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, ImageView> bdmColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, ImageView> iButtonColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, ImageView> printerColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, Integer> banknoteCountColumn;
    @FXML
    private TableColumn<TerminalInfoDTO, String> pingColumn;
    @FXML
    private TableView<CashUnitDTO> cashTable;
    @FXML
    private TableColumn<CashUnitDTO, String> currencyColumn;
    @FXML
    private TableColumn<CashUnitDTO, Integer> denominationColumn;
    @FXML
    private TableColumn<CashUnitDTO, Integer> quantityColumn;
    @FXML
    private TableColumn<CashUnitDTO, Integer> sumColumn;
    @FXML
    private TableView<TerminalLogDTO> messageTable;
    @FXML
    private TableColumn<TerminalLogDTO, String> messageDateColumn;
    @FXML
    private TableColumn<TerminalLogDTO, String> messageColumn;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @FXML
    public void initialize() {
        terminalTable.setItems(terminalService.getAllTerminalInfoDto());
        terminalIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTerminalID()));
        logicalNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogicalName()));
        hostColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHost()));
        portColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPort()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        overallStatusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new ImageView(
                new Image(cellData.getValue().getBanknoteModule().getStatus() == DeviceStatusConstraints.GlobalDeviceStatus.OK &&
                        cellData.getValue().getiButtonReader().getStatus() == DeviceStatusConstraints.GlobalDeviceStatus.OK ? "/images/ok.png" :
                        "/images/error.png")
        )));
        networkColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new ImageView(new Image("/images/bulbgreen.png"))));
        bdmColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new ImageView(
                new Image(cellData.getValue().getBanknoteModule().getStatus() == DeviceStatusConstraints.GlobalDeviceStatus.OK ?
                        "/images/ok.png" : "/images/error.png")
        )));
        iButtonColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new ImageView(
                new Image(cellData.getValue().getiButtonReader().getStatus() == DeviceStatusConstraints.GlobalDeviceStatus.OK ?
                        "/images/ok.png" : "/images/error.png")
        )));
        printerColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new ImageView(
                new Image(cellData.getValue().getPrinter().getStatus() == DeviceStatusConstraints.GlobalDeviceStatus.OK ?
                        "/images/ok.png" : "/images/error.png")
        )));
        banknoteCountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(getBanknoteCount(cellData.getValue().getBanknoteModule().getCashUnits())));
        pingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastPing() != null ?
                dateFormat.format(cellData.getValue().getLastPing()) : ""));
        terminalTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onTerminalChange(newValue)
        );

        currencyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrency()));
        denominationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDenomination()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
        sumColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(getCashUnitSum(cellData.getValue())));

        messageDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getTerminalDate())));
        messageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMessage()));
    }

    private int getBanknoteCount(List<CashUnitDTO> cashList) {
        int count = 0;
        for (CashUnitDTO dto : cashList) {
            count += dto.getQuantity();
        }
        return count;
    }

    private int getCashUnitSum(CashUnitDTO dto) {
        return dto.getDenomination() * dto.getQuantity();
    }

    private void onTerminalChange(TerminalInfoDTO dto) {
        if (dto != null) {
            ObservableList<CashUnitDTO> cashUnits = FXCollections.observableArrayList();
            ObservableList<TerminalLogDTO> logs = FXCollections.observableArrayList();
            cashUnits.addAll(dto.getBanknoteModule().getCashUnits());
            if (dto.getLastMessages() != null) {
                logs.addAll(dto.getLastMessages());
            }
            cashTable.setItems(cashUnits);
            messageTable.setItems(logs);
        }
    }
}
