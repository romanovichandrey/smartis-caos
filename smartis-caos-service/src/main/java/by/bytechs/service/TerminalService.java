package by.bytechs.service;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;
import by.bytechs.repository.entity.caos.Terminal;

import java.util.List;

/**
 * Created by Kotsuba_VV on 11.01.2017.
 */

public interface TerminalService extends Service<Terminal, TerminalDTO> {
    TerminalDTO getTerminalDTOByID(int id);
    List<TerminalDTO> getAllTerminalDTO();
    int saveTerminalDTO(TerminalDTO terminalDTO);

    TerminalInfoDTO getTerminalInfoDTOByID(int id);
    List<TerminalInfoDTO> getAllTerminalInfoDTO();
    int saveTerminalInfoDTO(TerminalInfoDTO dto);

    boolean isTerminalExists(String terminalID);
    Terminal saveTerminal(Terminal terminal);
    Terminal findByTerminalID(String terminalID);

    TerminalInfoDTO entityToInfoDTO(Terminal terminal);
    Terminal infoDTOtoEntity(TerminalInfoDTO terminalInfoDTO);
}
