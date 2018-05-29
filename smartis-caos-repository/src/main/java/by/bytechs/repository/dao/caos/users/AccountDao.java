package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.Account}
 *
 * @author Romanovich Andrei
 */
public interface AccountDao extends JpaRepository<Account, Integer> {
}