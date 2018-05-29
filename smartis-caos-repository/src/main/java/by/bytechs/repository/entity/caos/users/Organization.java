package by.bytechs.repository.entity.caos.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "Organizations")
public class Organization implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String legalAddress;
    @Column(nullable = false)
    private String organizationName;
    @Column
    private String phoneNumber;
    @OneToMany(mappedBy = "organization")
    private List<User> userList;
    @OneToMany(mappedBy = "organization")
    private List<Account> accountList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
