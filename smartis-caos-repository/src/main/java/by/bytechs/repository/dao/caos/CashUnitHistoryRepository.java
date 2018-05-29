package by.bytechs.repository.dao.caos;

import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kotsuba_VV on 26.01.2017.
 */

public interface CashUnitHistoryRepository extends JpaRepository<CashUnitHistoryHeader, Integer> {
    CashUnitHistoryHeader findLastByTerminal(Terminal terminal);
}
