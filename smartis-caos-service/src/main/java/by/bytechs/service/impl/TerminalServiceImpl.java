package by.bytechs.service.impl;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.dto.TerminalInfoDTO;
import by.bytechs.repository.dao.caos.TerminalRepository;
import by.bytechs.repository.entity.caos.*;
import by.bytechs.service.BanknoteService;
import by.bytechs.service.IButtonService;
import by.bytechs.service.PrinterService;
import by.bytechs.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kotsuba_VV on 10.01.2017.
 */

@Service
@Transactional
public class TerminalServiceImpl implements TerminalService {

    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private BanknoteService banknoteService;
    @Autowired
    private IButtonService iButtonService;
    @Autowired
    private PrinterService printerService;

    @Override
    public TerminalDTO getTerminalDTOByID(int id) {
        return entityToDTO(terminalRepository.findOne(id));
    }

    @Override
    public List<TerminalDTO> getAllTerminalDTO() {
        return terminalRepository.findAll().stream().map(terminal -> entityToDTO(terminal)).collect(Collectors.toList());
    }

    @Override
    public int saveTerminalDTO(TerminalDTO terminalDTO) {
        Terminal terminal = terminalRepository.save(dtoToEntity(terminalDTO));
        return terminal.getId();
    }

    @Override
    public TerminalInfoDTO getTerminalInfoDTOByID(int id) {
        return entityToInfoDTO(terminalRepository.findOne(id));
    }

    @Override
    public List<TerminalInfoDTO> getAllTerminalInfoDTO() {
        return terminalRepository.findAll().stream().map(terminal -> entityToInfoDTO(terminal)).collect(Collectors.toList());
    }

    @Override
    public int saveTerminalInfoDTO(TerminalInfoDTO dto) {
        Terminal terminal = terminalRepository.save(infoDTOtoEntity(dto));
        return terminal.getId();
    }

    @Override
    public boolean isTerminalExists(String terminalID) {
        return terminalRepository.findByTerminalID(terminalID) != null ? true : false;
    }

    @Override
    public Terminal saveTerminal(Terminal terminal) {
        return terminalRepository.save(terminal);
    }

    @Override
    public Terminal findByTerminalID(String terminalID) {
        return terminalRepository.findByTerminalID(terminalID);
    }

    @Override
    public TerminalDTO entityToDTO(Terminal terminal) {
        TerminalDTO terminalDTO = new TerminalDTO();
        terminalDTO.setId(terminal.getId());
        terminalDTO.setTerminalID(terminal.getTerminalID());
        terminalDTO.setLogicalName(terminal.getLogicalName());
        terminalDTO.setHost(terminal.getHost());
        terminalDTO.setPort(terminal.getPort());
        return terminalDTO;
    }

    @Override
    public Terminal dtoToEntity(TerminalDTO dto) {
        Date date = new Date();
        Terminal terminal = dto.getId() != 0 ? terminalRepository.findOne(dto.getId()) : new Terminal();
        terminal.setTerminalID(dto.getTerminalID(), date);
        terminal.setLogicalName(dto.getLogicalName(), date);
        terminal.setHost(dto.getHost(), date);
        terminal.setPort(dto.getPort(), date);
        return terminal;
    }

    @Override
    public TerminalInfoDTO entityToInfoDTO(Terminal terminal) {
        TerminalInfoDTO dto = new TerminalInfoDTO();
        dto.setId(terminal.getId());
        dto.setTerminalID(terminal.getTerminalID());
        dto.setLogicalName(terminal.getLogicalName());
        dto.setHost(terminal.getHost());
        dto.setPort(terminal.getPort());
        if (terminal.getTerminalSettings() != null) {
            dto.setCity(terminal.getTerminalSettings().getCity());
            dto.setAddress(terminal.getTerminalSettings().getAddress());
            dto.setLatitude(terminal.getTerminalSettings().getLatitude());
            dto.setLongitude(terminal.getTerminalSettings().getLongitude());
            dto.setModel(terminal.getTerminalSettings().getModel());
            dto.setCaosWWWVersion(terminal.getTerminalSettings().getCaosWWWVersion());
            dto.setCaosPMVersion(terminal.getTerminalSettings().getCaosPMVersion());
        }
        for (Device device : terminal.getDevices()) {
            if (device instanceof BanknoteModule) {
                dto.setBanknoteModule(banknoteService.entityToDTO((BanknoteModule) device));
            } else if (device instanceof IButtonReader) {
                dto.setiButtonReader(iButtonService.entityToDTO((IButtonReader) device));
            } else if (device instanceof Printer) {
                dto.setPrinter(printerService.entityToDTO((Printer) device));
            }
        }
        return dto;
    }

    @Override
    public Terminal infoDTOtoEntity(TerminalInfoDTO terminalInfoDTO) {
        Date date = new Date();
        Terminal terminal = terminalInfoDTO.getId() != 0 ? terminalRepository.findOne(terminalInfoDTO.getId()) : new Terminal();
        terminal.setTerminalID(terminalInfoDTO.getTerminalID(), date);
        terminal.setLogicalName(terminalInfoDTO.getLogicalName(), date);
        terminal.setHost(terminalInfoDTO.getHost(), date);
        terminal.setPort(terminalInfoDTO.getPort(), date);
        terminal.getTerminalSettings().setCity(terminalInfoDTO.getCity(), date);
        terminal.getTerminalSettings().setAddress(terminalInfoDTO.getAddress(), date);
        terminal.getTerminalSettings().setLatitude(terminalInfoDTO.getLatitude(), date);
        terminal.getTerminalSettings().setLongitude(terminalInfoDTO.getLongitude(), date);
        terminal.getTerminalSettings().setModel(terminalInfoDTO.getModel(), date);
        terminal.getTerminalSettings().setCaosWWWVersion(terminalInfoDTO.getCaosWWWVersion(), date);
        terminal.getTerminalSettings().setCaosPMVersion(terminalInfoDTO.getCaosPMVersion(), date);
        return terminal;
    }
}
