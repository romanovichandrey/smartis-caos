package by.bytechs.repository.entity.caos.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
@Entity
@Table(name = "UsersTypes")
public class UserType implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    private Integer typeId;
    @Column(nullable = false)
    private String typeName;
    @OneToMany(mappedBy = "userType")
    private List<User> userList;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
