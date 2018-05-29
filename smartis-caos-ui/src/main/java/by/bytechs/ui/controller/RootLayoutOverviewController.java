package by.bytechs.ui.controller;

import by.bytechs.ui.MainApp;
import by.bytechs.ui.config.javaFxConfig.FXMLController;
import by.bytechs.ui.view.AddTerminalView;
import by.bytechs.ui.view.ManagerUsersView;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Romanovich Andrei
 */
@FXMLController
public class RootLayoutOverviewController {
    @Autowired
    private ManagerUsersOverviewController managerUsersOverviewController;
    @Autowired
    private AddTerminalOverviewController addTerminalOverviewController;

    @FXML
    public void usersManagerOverview() {
        managerUsersOverviewController.update();
        MainApp.showView(ManagerUsersView.class, Modality.NONE, null);
    }

    @FXML
    public void addTerminalOverview() {
        MainApp.showView(AddTerminalView.class, Modality.APPLICATION_MODAL, MainApp.getStage());
        addTerminalOverviewController.update();
    }


}
