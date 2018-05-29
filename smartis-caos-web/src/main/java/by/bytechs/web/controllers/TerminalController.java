package by.bytechs.web.controllers;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;
import by.bytechs.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Kotsuba_VV on 18.01.2017.
 */

@RestController
@RequestMapping(value = "/terminals")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @RequestMapping(method = RequestMethod.GET, value = "getTerminalInfoByID/{id}", headers = "Accept=application/json", produces = "application/json")
    public TerminalInfoDTO getTerminalInfoByID(@PathVariable int id) {
        return terminalService.getTerminalInfoDTOByID(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllTerminalsInfo/", headers = "Accept=application/json", produces = "application/json")
    public List<TerminalInfoDTO> getAllTerminalInfo() {
        return terminalService.getAllTerminalInfoDTO();
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveTerminalInfo/", headers = "Accept=application/json", produces = "application/json")
    public int saveTerminalInfo(@RequestBody TerminalInfoDTO dto) {
        return terminalService.saveTerminalInfoDTO(dto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTerminalByID/{id}", headers = "Accept=application/json", produces = "application/json")
    public TerminalDTO getTerminalByID(@PathVariable int id) {
        return terminalService.getTerminalDTOByID(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllTerminals/", headers = "Accept=application/json", produces = "application/json")
    public List<TerminalDTO> getAllTerminals() {
        return terminalService.getAllTerminalDTO();
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveTerminal/", headers = "Accept=application/json", produces = "application/json")
    public int saveTerminal(@RequestBody TerminalDTO terminalDTO) {
        return terminalService.saveTerminalDTO(terminalDTO);
    }


}
