package by.bytechs.repository.dao.external;

import by.bytechs.repository.entity.external.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Interface for a Data Access {@link SecurityRole}
 *
 * @author Romanovich Andrei
 */
@Repository
public interface SecurityRoleDao extends JpaRepository<SecurityRole, Integer> {
}