package by.bytechs.repository.dao.external;

import by.bytechs.repository.entity.external.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Interface for a Data Access {@link SecurityGroup}
 *
 * @author Romanovich Andrei
 */
@Repository
public interface SecurityGroupDao extends JpaRepository<SecurityGroup, Integer> {

    /**
     * <p>Find {@link SecurityGroup} by group name.
     *
     * @param groupName
     */
    SecurityGroup findByGroupName(String groupName);
}