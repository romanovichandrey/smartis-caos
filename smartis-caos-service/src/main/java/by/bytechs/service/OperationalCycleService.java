package by.bytechs.service;

import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.CurrentOperationalCycle;
import by.bytechs.repository.entity.caos.Terminal;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */
public interface OperationalCycleService {
    CurrentOperationalCycle openOperationalCycle(Terminal terminal, Date dateStart);
    void closeOperationalCycle(Terminal terminal, Date dateEnd, CashUnitHistoryHeader header);
}
