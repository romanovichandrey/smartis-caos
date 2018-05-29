package by.bytechs.ui.service;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;
import by.bytechs.ui.restService.interfaces.TerminalRestService;
import by.bytechs.ui.service.interfaces.TerminalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Service
public class TerminalServiceImpl implements TerminalService {

    @Autowired
    private TerminalRestService terminalRestService;

    @Override
    public ObservableList<TerminalDTO> getAllTerminals() {
        List<TerminalDTO> terminalDTOList = terminalRestService.getAllTerminals();
        ObservableList<TerminalDTO> resultList = FXCollections.observableArrayList(terminalDTOList);
        return resultList;
    }

    @Override
    public ObservableList<TerminalInfoDTO> getAllTerminalInfoDto() {
        List<TerminalInfoDTO> terminalInfoDTOList = terminalRestService.getAllTerminalInfoDto();
        if (terminalInfoDTOList != null) {
            ObservableList<TerminalInfoDTO> resultList = FXCollections.observableArrayList(terminalInfoDTOList);
            return resultList;
        }
        return FXCollections.observableArrayList();
    }
}
