package by.bytechs.service.impl;

import by.bytechs.dto.CashUnitHistoryDTO;
import by.bytechs.repository.dao.caos.CashUnitHistoryRepository;
import by.bytechs.repository.entity.caos.CashUnit;
import by.bytechs.repository.entity.caos.CashUnitHistory;
import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.service.CashUnitHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kotsuba_VV on 27.01.2017.
 */

@Service
@Transactional
public class CashUnitHistoryServiceImpl implements CashUnitHistoryService {

    @Autowired
    private CashUnitHistoryRepository historyRepository;

    @Override
    public void saveHistory(CashUnitHistoryHeader header) {
        historyRepository.save(header);
    }

    @Override
    public List<CashUnitHistory> convertCashUnitsToHistory(List<CashUnit> cashUnits) {
        List<CashUnitHistory> historyList = new ArrayList<>();
        for (CashUnit cashUnit : cashUnits) {
            CashUnitHistory history = new CashUnitHistory();
            history.setCurrency(cashUnit.getCurrency());
            history.setDenomination(cashUnit.getDenomination());
            history.setQuantity(cashUnit.getQuantity());
            history.setType(cashUnit.getType());
            historyList.add(history);
        }
        return historyList;
    }

    @Override
    public CashUnitHistoryHeader findLastByTerminal(Terminal terminal) {
        return historyRepository.findLastByTerminal(terminal);
    }

    @Override
    public CashUnitHistoryDTO entityToDTO(CashUnitHistoryHeader entity) {
        return null;
    }

    @Override
    public CashUnitHistoryHeader dtoToEntity(CashUnitHistoryDTO dto) {
        return null;
    }
}
