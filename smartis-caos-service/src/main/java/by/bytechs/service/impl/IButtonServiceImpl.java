package by.bytechs.service.impl;

import by.bytechs.dto.IButtonReaderDTO;
import by.bytechs.repository.dao.caos.DeviceRepository;
import by.bytechs.repository.entity.caos.IButtonReader;
import by.bytechs.service.IButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kotsuba_VV on 23.01.2017.
 */

@Service
@Transactional
public class IButtonServiceImpl implements IButtonService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public IButtonReaderDTO entityToDTO(IButtonReader entity) {
        IButtonReaderDTO dto = new IButtonReaderDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public IButtonReader dtoToEntity(IButtonReaderDTO dto) {
        return null;
    }

    @Override
    public void saveDevice(IButtonReader device) {
        deviceRepository.save(device);
    }
}
