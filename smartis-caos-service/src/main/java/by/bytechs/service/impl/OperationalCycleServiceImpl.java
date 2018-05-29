package by.bytechs.service.impl;

import by.bytechs.repository.dao.caos.CurrentOperationalCycleRepository;
import by.bytechs.repository.dao.caos.OperationalCycleHistoryRepository;
import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.CurrentOperationalCycle;
import by.bytechs.repository.entity.caos.OperationalCycleHistory;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.service.OperationalCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */

@Service
@Transactional
public class OperationalCycleServiceImpl implements OperationalCycleService {
    @Autowired
    private CurrentOperationalCycleRepository currentOperationalCycleRepository;
    @Autowired
    private OperationalCycleHistoryRepository historyRepository;

    @Override
    public CurrentOperationalCycle openOperationalCycle(Terminal terminal, Date dateStart) {
        CurrentOperationalCycle last = terminal.getCurrentOperationalCycle();
        if (last != null) {
            currentOperationalCycleRepository.delete(terminal.getCurrentOperationalCycle().getId());
        }

        CurrentOperationalCycle cycle = new CurrentOperationalCycle();
        cycle.setDateStart(dateStart);
        cycle.setTerminal(terminal);
        currentOperationalCycleRepository.save(cycle);
        return cycle;
    }

    @Override
    public void closeOperationalCycle(Terminal terminal, Date dateEnd, CashUnitHistoryHeader header) {
        CurrentOperationalCycle currentCycle = terminal.getCurrentOperationalCycle();

        if (currentCycle != null && currentCycle.getDateStart() != null) {
            OperationalCycleHistory history = new OperationalCycleHistory();
            history.setTerminal(terminal);
            history.setDateStart(currentCycle.getDateStart());
            history.setDateEnd(dateEnd);
            history.setHeader(header);
            historyRepository.save(history);
            currentOperationalCycleRepository.delete(currentCycle.getId());
        }
    }
}
