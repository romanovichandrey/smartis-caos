package by.bytechs.service;

import by.bytechs.dto.TerminalLogDTO;
import by.bytechs.repository.entity.caos.TerminalLog;

/**
 * Created by Kotsuba_VV on 14.02.2017.
 */
public interface TerminalLogService extends Service<TerminalLog, TerminalLogDTO> {
    void saveLog(TerminalLog log);
}
