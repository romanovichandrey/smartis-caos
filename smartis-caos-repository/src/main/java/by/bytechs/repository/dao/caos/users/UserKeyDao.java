package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.UserKey}
 *
 * @author Romanovich Andrei
 */
public interface UserKeyDao extends JpaRepository<UserKey, String> {
}