package by.bytechs.service.impl;

import by.bytechs.dto.TerminalLogDTO;
import by.bytechs.repository.dao.caos.TerminalLogRepository;
import by.bytechs.repository.entity.caos.TerminalLog;
import by.bytechs.service.TerminalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kotsuba_VV on 14.02.2017.
 */

@Service
@Transactional
public class TerminalLogServiceImpl implements TerminalLogService {
    @Autowired
    private TerminalLogRepository terminalLogRepository;

    @Override
    public TerminalLogDTO entityToDTO(TerminalLog entity) {
        TerminalLogDTO dto = new TerminalLogDTO();
        dto.setId(entity.getId());
        dto.setTerminalID(entity.getTerminalID());
        dto.setMessage(entity.getMessage());
        dto.setMessageSource(entity.getMessageSource());
        dto.setTerminalDate(entity.getTerminalDate());
        dto.setServerDate(entity.getServerDate());
        return dto;
    }

    @Override
    public TerminalLog dtoToEntity(TerminalLogDTO dto) {
        return null;
    }


    @Override
    public void saveLog(TerminalLog log) {
        terminalLogRepository.save(log);
    }
}
