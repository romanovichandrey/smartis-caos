package by.bytechs.ui.restService.interfaces;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;

import java.util.List;

/**
 * <p>
 * The service sends requests for information about the terminals.
 *
 * @author Romanovich Andrei
 */
public interface TerminalRestService {

    /**
     * <p>
     * Receive all terminals registered in the system.
     */
    List<TerminalDTO> getAllTerminals();

    List<TerminalInfoDTO> getAllTerminalInfoDto();
}
