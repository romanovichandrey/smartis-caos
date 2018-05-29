package by.bytechs.repository.dao.caos.users;

import by.bytechs.repository.entity.caos.users.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * Interface for a Data Access {@link by.bytechs.repository.entity.caos.users.Organization}
 *
 * @author Romanovich Andrei
 */
public interface OrganizationDao extends JpaRepository<Organization, Integer> {
}