package by.bytechs.ui.controller;

import by.bytechs.dto.users.*;
import by.bytechs.ui.config.javaFxConfig.FXMLController;
import by.bytechs.ui.service.interfaces.PasswordDigestService;
import by.bytechs.ui.service.interfaces.UserService;
import by.bytechs.ui.view.AddUserView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Romanovich Andrei
 */
@FXMLController
public class AddUserOverviewController {
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox passwordIsChangeCheckBox;
    @FXML
    private ComboBox<OrganizationDto> organizationComboBox;
    @FXML
    private ComboBox<UserTypeDto> typeUserComboBox;
    @FXML
    private TextField keyIdTextField;
    @FXML
    private ComboBox<AccountDto> accountComboBox;
    @FXML
    private Button addKeyIdButton;
    @FXML
    private Button addUserButton;
    @FXML
    private TextArea legalAddressTextArea;
    @FXML
    private TextField organizationNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TableView<AccountDto> accountTable;
    @FXML
    private TableColumn<AccountDto, String> accountNumberColumn;
    @FXML
    private TableColumn<AccountDto, String> accountCurrencyColumn;
    @FXML
    private TextField accountNumberTextField;
    @FXML
    private TextField currencyTextField;
    @FXML
    private Button addAccountButton;
    @FXML
    private Button updateOrganizationButton;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private TableView<UserKeyDto> keyIdTable;
    @FXML
    private TableColumn<UserKeyDto, String> keyIdColumn;
    @FXML
    private TableColumn<UserKeyDto, String> keyIdAccountColumn;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordDigestService passwordDigestService;
    @Autowired
    private AddUserView addUserView;
    @Autowired
    private ManagerUsersOverviewController managerUsersOverviewController;
    private ObservableList<OrganizationDto> organizationList;
    private ObservableList<UserTypeDto> userTypeList;

    @FXML
    public void initialize() {
        organizationComboBox.setItems(organizationList);
        typeUserComboBox.setItems(userTypeList);
        accountComboBox.setItems(FXCollections.observableArrayList());

        accountTable.setItems(FXCollections.observableArrayList());
        accountNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccountNumber()));
        accountCurrencyColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccountCurrency()));
        accountTableRegistrationListeners();

        keyIdTable.setItems(FXCollections.observableArrayList());
        keyIdColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKeyId()));
        keyIdAccountColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAccountNumber()));
    }

    @FXML
    public void organizationComboBoxAction() {
        deleteAccountButton.setDisable(true);
        OrganizationDto dto = organizationComboBox.getSelectionModel().getSelectedItem();
        if (dto != null) {
            if (dto.getId() != -1) {
                organizationNameTextField.setText(dto.getName() != null ? dto.getName() : "");
                addAccountButton.setDisable(false);
                accountComboBox.setItems(FXCollections.observableArrayList(dto.getAccountDtoList()));
            } else {
                organizationNameTextField.setText("");
                addAccountButton.setDisable(true);
                accountComboBox.getItems().clear();
            }
            legalAddressTextArea.setText(dto.getLegalAddress() != null ? dto.getLegalAddress() : "");
            phoneNumberTextField.setText(dto.getPhoneNumber() != null ? dto.getPhoneNumber() : "");
            if (dto.getAccountDtoList() != null && !dto.getAccountDtoList().isEmpty()) {
                ObservableList<AccountDto> accountDtoObservableList = FXCollections.observableArrayList(dto.getAccountDtoList());
                accountTable.setItems(accountDtoObservableList);
            } else {
                accountTable.setItems(FXCollections.observableArrayList());
            }
        }
    }

    @FXML
    public void deleteAccountAction() {
        AccountDto dto = accountTable.getSelectionModel().getSelectedItem();
        OrganizationDto organizationDto = organizationComboBox.getSelectionModel().getSelectedItem();
        if (userService.deleteAccount(dto.getId())) {
            accountTable.getItems().remove(dto);
            accountComboBox.getItems().remove(dto);
            organizationDto.getAccountDtoList().remove(dto);
        }
    }

    @FXML
    public void addAccountAction() {
        OrganizationDto dto = organizationComboBox.getSelectionModel().getSelectedItem();
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(accountNumberTextField.getText());
        accountDto.setAccountCurrency(currencyTextField.getText());
        if (dto.getAccountDtoList() == null) {
            dto.setAccountDtoList(new ArrayList<>());
        }
        accountDto = userService.addNewAccount(accountDto, dto.getId());
        dto.getAccountDtoList().add(accountDto);
        ObservableList<AccountDto> accountDtoObservableList = FXCollections.observableArrayList(dto.getAccountDtoList());
        accountTable.getItems().clear();
        accountTable.getItems().addAll(accountDtoObservableList);

        accountNumberTextField.clear();
        currencyTextField.clear();

        accountComboBox.getItems().add(accountDto);
    }

    @FXML
    public void updateOrganizationAction() {
        ObservableList<OrganizationDto> organizationDtoObservableList = FXCollections.observableArrayList(organizationComboBox.getItems());
        OrganizationDto dto = organizationComboBox.getSelectionModel().getSelectedItem();
        if (dto.getName().equals("Добавить новую организацию")) {
            dto = new OrganizationDto();
            organizationDtoObservableList.add(dto);
        }
        dto.setName(organizationNameTextField.getText());
        dto.setLegalAddress(legalAddressTextArea.getText());
        dto.setPhoneNumber(phoneNumberTextField.getText());
        dto = userService.addOrUpdateOrganization(dto);
        organizationComboBox.getItems().clear();
        organizationComboBox.getItems().addAll(organizationDtoObservableList);
        organizationComboBox.getSelectionModel().select(dto);
    }

    @FXML
    public void addKeyIdAction() {
        AccountDto accountDto = accountComboBox.getSelectionModel().getSelectedItem();
        UserKeyDto userKeyDto = new UserKeyDto(keyIdTextField.getText(), accountDto.getId(), accountDto.getAccountNumber(),
                accountDto.getAccountCurrency());
        keyIdTable.getItems().add(userKeyDto);
        keyIdTextField.clear();
        accountComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void addUserAction() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(firstNameTextField.getText());
        userDto.setLastName(lastNameTextField.getText());
        userDto.setMiddleName(middleNameTextField.getText());
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (password.equals(confirmPassword)) {
            userDto.setUserPasswordHash(passwordDigestService.getPasswordHash(password));
            userDto.setPasswordChangeDate(new Date());
            userDto.setPasswordIsChange(passwordIsChangeCheckBox.isSelected());
            userDto.setUserTypeDto(typeUserComboBox.getSelectionModel().getSelectedItem());
            userDto.setUserKeyDtoList(keyIdTable.getItems());
            userDto.setOrganizationDto(organizationComboBox.getSelectionModel().getSelectedItem());
            userDto = userService.addNewUser(userDto);
            if (userDto != null) {
                managerUsersOverviewController.getUsers().add(userDto);
                addUserView.closeView();
            }
        }
    }

    private void accountTableRegistrationListeners() {
        accountTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                deleteAccountButton.setDisable(false);
            } else {
                deleteAccountButton.setDisable(true);
            }
        });
    }

    public void update() {
        organizationList = userService.getAllOrganization();
        userTypeList = userService.getAllUserType();
    }
}