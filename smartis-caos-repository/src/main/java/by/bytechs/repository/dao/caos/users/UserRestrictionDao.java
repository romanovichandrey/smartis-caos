package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.UserRestriction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.UserRestriction}
 *
 * @author Romanovich Andrei
 */
public interface UserRestrictionDao extends JpaRepository<UserRestriction, Integer> {
}