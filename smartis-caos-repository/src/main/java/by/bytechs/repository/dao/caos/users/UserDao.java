package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.User}
 *
 * @author Romanovich Andrei
 */
public interface UserDao extends JpaRepository<User, Integer> {
}