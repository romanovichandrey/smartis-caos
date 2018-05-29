package by.bytechs.repository.dao.caos;

import by.bytechs.repository.entity.caos.CurrentOperationalCycle;
import by.bytechs.repository.entity.caos.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */
public interface CurrentOperationalCycleRepository extends JpaRepository<CurrentOperationalCycle, Integer> {
    void deleteByTerminal(Terminal terminal);
}
