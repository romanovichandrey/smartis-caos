package by.bytechs.service.impl;

import by.bytechs.dto.BanknoteModuleDTO;
import by.bytechs.repository.dao.caos.DeviceRepository;
import by.bytechs.repository.dao.caos.TerminalRepository;
import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.Device;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.service.BanknoteService;
import by.bytechs.service.CashUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Kotsuba_VV on 23.01.2017.
 */

@Service
@Transactional
public class BanknoteServiceImpl implements BanknoteService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private CashUnitService cashUnitService;

    @Override
    public void saveBanknoteModule(BanknoteModuleDTO dto) {
        deviceRepository.save(dtoToEntity(dto));
    }

    @Override
    public BanknoteModuleDTO getBanknoteModuleByTerminal(String terminalID) {
        Terminal terminal = terminalRepository.findByTerminalID(terminalID);
        for (Device device : terminal.getDevices()) {
            if (device instanceof BanknoteModule) {
                return entityToDTO((BanknoteModule) device);
            }
        }
        return null;
    }

    @Override
    public BanknoteModuleDTO entityToDTO(BanknoteModule entity) {
        BanknoteModuleDTO dto = new BanknoteModuleDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setState(entity.getState());
        dto.setCleaning(entity.getCleaning());
        dto.setDoor(entity.getDoor());
        dto.setBagStatus(entity.getBagStatus());
        dto.setBundlerStatus(entity.getBundlerStatus());
        dto.setCover(entity.getCover());
        dto.setCashUnits(entity.getCurrentCashUnits().stream().map(cashUnit -> cashUnitService.entityToDTO(cashUnit)).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public BanknoteModule dtoToEntity(BanknoteModuleDTO dto) {
        Date date = new Date();
        BanknoteModule banknoteModule = (BanknoteModule) deviceRepository.findOne(dto.getId());
        banknoteModule.setId(dto.getId());
        banknoteModule.setStatus(dto.getStatus(), date);
        banknoteModule.setState(dto.getState(), date);
        banknoteModule.setCleaning(dto.getCleaning(), date);
        banknoteModule.setDoor(dto.getDoor(), date);
        banknoteModule.setBagStatus(dto.getBagStatus(), date);
        banknoteModule.setBundlerStatus(dto.getBundlerStatus(), date);
        banknoteModule.setCover(dto.getCover(), date);
        return banknoteModule;
    }

    @Override
    public void saveDevice(BanknoteModule device) {
        deviceRepository.save(device);
    }
}
