package by.bytechs.ui.service.interfaces;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;
import javafx.collections.ObservableList;

/**
 * @author Romanovich Andrei
 */
public interface TerminalService {

    ObservableList<TerminalDTO> getAllTerminals();
    ObservableList<TerminalInfoDTO> getAllTerminalInfoDto();
}
