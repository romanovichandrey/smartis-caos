package by.bytechs.dto.users;

import java.io.Serializable;

/**
 * @author Romanovich Andrei
 */
public class UserTypeDto implements Serializable {
    private Integer typeId;
    private String typeName;

    public UserTypeDto() {
    }

    public UserTypeDto(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

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

    @Override
    public String toString() {
        return typeName;
    }
}