package by.bytechs.repository.entity.caos.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String accountCurrency;
    @ManyToOne
    @JoinColumn(name = "relatedOrganizationId")
    private Organization organization;
    @OneToMany(mappedBy = "account")
    private List<UserKey> userKeyList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
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
}
