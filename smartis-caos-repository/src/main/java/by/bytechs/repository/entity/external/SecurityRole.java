package by.bytechs.repository.entity.external;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table
public class SecurityRole implements Serializable, Comparable<SecurityRole> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column(nullable = false)
    private String roleName;
    @ManyToMany(mappedBy = "securityRoleSet")
    private Set<SecurityGroup> securityGroupSet;

    public SecurityRole() {
    }

    public SecurityRole(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SecurityGroup> getSecurityGroupSet() {
        return securityGroupSet;
    }

    public void setSecurityGroupSet(Set<SecurityGroup> securityGroupSet) {
        this.securityGroupSet = securityGroupSet;
    }

    @Override
    public int compareTo(SecurityRole o) {
        if (this.getId() == null && o.getId() != null) {
            return -1;
        } else if (this.getId() != null && o.getId() == null) {
            return 1;
        } else if (!this.getId().equals(o.getId())) {
            return this.getId().compareTo(o.getId());
        }

        if (this.getRoleName() == null && o.getRoleName() != null) {
            return -1;
        } else if (this.getRoleName() != null && o.getRoleName() == null) {
            return 1;
        } else if (!this.getRoleName().equals(o.getRoleName())) {
            return this.getRoleName().compareTo(o.getRoleName());
        }

        return 0;
    }
}