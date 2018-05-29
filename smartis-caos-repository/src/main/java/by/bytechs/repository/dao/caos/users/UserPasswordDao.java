package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.UserPassword}
 *
 * @author Romanovich Andrei
 */
public interface UserPasswordDao extends JpaRepository<UserPassword, Integer> {
}