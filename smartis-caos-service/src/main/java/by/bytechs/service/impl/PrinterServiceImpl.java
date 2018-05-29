package by.bytechs.service.impl;

import by.bytechs.dto.PrinterDTO;
import by.bytechs.repository.dao.caos.DeviceRepository;
import by.bytechs.repository.entity.caos.Printer;
import by.bytechs.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kotsuba_VV on 23.01.2017.
 */

@Service
@Transactional
public class PrinterServiceImpl implements PrinterService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public PrinterDTO entityToDTO(Printer entity) {
        PrinterDTO dto = new PrinterDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public Printer dtoToEntity(PrinterDTO dto) {
        return null;
    }

    @Override
    public void saveDevice(Printer device) {
        deviceRepository.save(device);
    }
}
