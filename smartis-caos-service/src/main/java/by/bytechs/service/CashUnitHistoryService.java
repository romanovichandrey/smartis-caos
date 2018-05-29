package by.bytechs.service;

import by.bytechs.dto.CashUnitHistoryDTO;
import by.bytechs.repository.entity.caos.CashUnit;
import by.bytechs.repository.entity.caos.CashUnitHistory;
import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.Terminal;

import java.util.List;

/**
 * Created by Kotsuba_VV on 27.01.2017.
 */

public interface CashUnitHistoryService extends Service<CashUnitHistoryHeader, CashUnitHistoryDTO> {
    void saveHistory(CashUnitHistoryHeader header);
    List<CashUnitHistory> convertCashUnitsToHistory(List<CashUnit> cashUnits);
    CashUnitHistoryHeader findLastByTerminal(Terminal terminal);
}
