package by.bytechs.repository.entity.external;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table
public class SecurityGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;
    @Column(nullable = false)
    private String groupName;
    @ManyToMany
    @JoinTable(name = "GroupToRoleSecurity",
                joinColumns = {@JoinColumn(name = "groupId")},
                inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private Set<SecurityRole> securityRoleSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<SecurityRole> getSecurityRoleSet() {
        return securityRoleSet;
    }

    public void setSecurityRoleSet(Set<SecurityRole> securityRoleSet) {
        this.securityRoleSet = securityRoleSet;
    }
}