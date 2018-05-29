package by.bytechs.repository.dao.caos;

import by.bytechs.repository.entity.caos.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kotsuba_VV on 11.01.2017.
 */

public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
    Terminal findByTerminalID(String terminalID);
}
