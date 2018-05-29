package by.bytechs.repository.entity.caos.users;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "UsersRestrictions")
public class UserRestriction implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(generator = "userGen")
    @GenericGenerator(name = "userGen", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    private Integer userId;
    @Column(nullable = false)
    private boolean passwordIsChange;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isPasswordIsChange() {
        return passwordIsChange;
    }

    public void setPasswordIsChange(boolean passwordIsChange) {
        this.passwordIsChange = passwordIsChange;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
