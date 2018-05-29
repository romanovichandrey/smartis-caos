package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.UserType}
 *
 * @author Romanovich Andrei
 */
public interface UserTypeDao extends JpaRepository<UserType, Integer> {
}