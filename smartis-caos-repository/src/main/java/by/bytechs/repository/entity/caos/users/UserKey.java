package by.bytechs.repository.entity.caos.users;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "UsersKeys")
public class UserKey implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    private String keyId;
    @Column(nullable = false)
    private boolean disabled;
    @ManyToOne
    @JoinColumn(name = "relatedUserId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "relatedAccountId")
    private Account account;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
