package by.bytechs.repository.entity.caos.users;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "UsersPasswords")
public class UserPassword implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(generator = "userGen")
    @GenericGenerator(name = "userGen", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    private Integer userId;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private Date passwordChangeDate;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
