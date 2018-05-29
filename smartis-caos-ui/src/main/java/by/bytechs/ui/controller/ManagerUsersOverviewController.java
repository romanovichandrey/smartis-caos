package by.bytechs.ui.controller;

import by.bytechs.dto.users.OrganizationDto;
import by.bytechs.dto.users.UserDto;
import by.bytechs.dto.users.UserKeyDto;
import by.bytechs.ui.MainApp;
import by.bytechs.ui.config.javaFxConfig.FXMLController;
import by.bytechs.ui.service.interfaces.UserService;
import by.bytechs.ui.view.AddUserView;
import by.bytechs.ui.view.ManagerUsersView;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Romanovich Andrei
 */
@FXMLController
public class ManagerUsersOverviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ManagerUsersView managerUsersView;
    @FXML
    private TextField searchUsersTextField;
    @FXML
    private ComboBox<String> criteriaSearchUsersComboBox;
    @FXML
    private ImageView addUserImage;
    @FXML
    private ImageView deleteUserImage;
    @FXML
    private ImageView editUserImage;
    @FXML
    private TableView<UserDto> usersTable;
    @FXML
    private TableColumn<UserDto, String> fullNameUserColumn;
    @FXML
    private TableColumn<UserDto, String> typeUserColumn;
    @FXML
    private TableColumn<UserDto, String> organizationNameColumn;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField typeUserTextField;
    @FXML
    private CheckBox passwordIsChangeCheckBox;
    @FXML
    private TableView<UserKeyDto> usersKeysTable;
    @FXML
    private TableColumn<UserKeyDto, String> keyIdColumn;
    @FXML
    private TableColumn<UserKeyDto, Boolean> activeKeyColumn;
    @FXML
    private TableColumn<UserKeyDto, String> accountNumberColumn;
    @FXML
    private TableColumn<UserKeyDto, String> accountCurrencyColumn;
    @FXML
    private TextField organizationNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextArea legalAddressTextArea;
    @FXML
    private HBox deleteUserImageHBox;
    @Autowired
    private AddUserOverviewController addUserOverviewController;
    private ObservableList<UserDto> userDtoObservableList;

    @FXML
    public void initialize() {
        ObservableList<String> criteriaSearchList = FXCollections.observableArrayList("Идентификатор", "Фамилия", "Имя",
                "Тип пользователя", "Организация");
        criteriaSearchUsersComboBox.setItems(criteriaSearchList);

        usersTable.setItems(getUsers());
        fullNameUserColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName() + " " +
                param.getValue().getFirstName().substring(0, 1) + "." + param.getValue().getMiddleName().substring(0, 1) + "."));
        typeUserColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUserTypeDto().getTypeName()));
        organizationNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOrganizationDto().getName()));
        usersKeysTable.setItems(FXCollections.observableArrayList());
        keyIdColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKeyId()));
        activeKeyColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isDisabled()));
        accountNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccountNumber()));
        accountCurrencyColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccountCurrency()));
        usersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteUserImageHBox.setDisable(false);
                usersKeysTable.getItems().clear();
                if (newValue.getUserKeyDtoList() != null) {
                    usersKeysTable.setItems(FXCollections.observableArrayList(newValue.getUserKeyDtoList()));
                }
                lastNameTextField.setText(newValue.getLastName() != null ? newValue.getLastName() : "");
                firstNameTextField.setText(newValue.getFirstName() != null ? newValue.getFirstName() : "");
                middleNameTextField.setText(newValue.getMiddleName() != null ? newValue.getMiddleName() : "");
                if (newValue.getUserTypeDto() != null && newValue.getUserTypeDto().getTypeName() != null) {
                    typeUserTextField.setText(newValue.getUserTypeDto().getTypeName());
                }
                passwordIsChangeCheckBox.setSelected(newValue.isPasswordIsChange());
                if (newValue.getOrganizationDto() != null) {
                    OrganizationDto dto = newValue.getOrganizationDto();
                    organizationNameTextField.setText(dto.getName() != null ? dto.getName() : "");
                    legalAddressTextArea.setText(dto.getLegalAddress() != null ? dto.getLegalAddress() : "");
                    phoneNumberTextField.setText(dto.getPhoneNumber() != null ? dto.getPhoneNumber() : "");
                }

            } else {
                deleteUserImageHBox.setDisable(true);
            }
        });
    }

    @FXML
    public void addUserMousePressedEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            addUserImage.setFitWidth(22);
            addUserImage.setFitHeight(15);
        }
    }

    @FXML
    public void addUserMouseReleasedEvent() {
        addUserImage.setFitWidth(27);
        addUserImage.setFitHeight(20);
        addUserOverviewController.update();
        MainApp.showView(AddUserView.class, Modality.APPLICATION_MODAL, (Stage) managerUsersView.getView().getScene().getWindow());
    }

    @FXML
    public void deleteUserMousePressedEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            deleteUserImage.setFitWidth(22);
            deleteUserImage.setFitHeight(15);
        }
    }

    @FXML
    public void deleteUserMouseReleasedEvent() {
        deleteUserImage.setFitWidth(27);
        deleteUserImage.setFitHeight(20);
        UserDto userDto = usersTable.getSelectionModel().getSelectedItem();
        if (userService.deleteUser(userDto.getId())) {
            usersTable.getItems().remove(userDto);
        }
    }

    @FXML
    public void editUserMousePressedEvent(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            editUserImage.setFitWidth(22);
            editUserImage.setFitHeight(15);
        }
    }

    @FXML
    public void editUserMouseReleasedEvent() {
        editUserImage.setFitWidth(27);
        editUserImage.setFitHeight(20);
    }

    public void update() {
        userDtoObservableList = FXCollections.synchronizedObservableList(userService.getAllUsers());
    }

    public ObservableList<UserDto> getUsers() {
        return userDtoObservableList;
    }
}
