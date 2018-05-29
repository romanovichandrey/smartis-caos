package by.bytechs.repository.entity.caos.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String middleName;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserPassword userPassword;
    @ManyToOne
    @JoinColumn(name = "relatedOrganizationId")
    private Organization organization;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserKey> userKeyList;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserRestriction userRestriction;
    @ManyToOne
    @JoinColumn(name = "relatedUserTypeId")
    private UserType userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<UserKey> getUserKeyList() {
        return userKeyList;
    }

    public void setUserKeyList(List<UserKey> userKeyList) {
        this.userKeyList = userKeyList;
    }

    public UserRestriction getUserRestriction() {
        return userRestriction;
    }

    public void setUserRestriction(UserRestriction userRestriction) {
        this.userRestriction = userRestriction;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
