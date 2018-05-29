package by.bytechs.dto.users;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
public class UserDto implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String userInfo;
    private String userPasswordHash;
    private Date passwordChangeDate;
    private boolean passwordIsChange;
    private boolean disabled;
    private List<UserKeyDto> userKeyDtoList;
    private UserTypeDto userTypeDto;
    private OrganizationDto organizationDto;

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

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public boolean isPasswordIsChange() {
        return passwordIsChange;
    }

    public void setPasswordIsChange(boolean passwordIsChange) {
        this.passwordIsChange = passwordIsChange;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<UserKeyDto> getUserKeyDtoList() {
        return userKeyDtoList;
    }

    public void setUserKeyDtoList(List<UserKeyDto> userKeyDtoList) {
        this.userKeyDtoList = userKeyDtoList;
    }

    public UserTypeDto getUserTypeDto() {
        return userTypeDto;
    }

    public void setUserTypeDto(UserTypeDto userTypeDto) {
        this.userTypeDto = userTypeDto;
    }

    public OrganizationDto getOrganizationDto() {
        return organizationDto;
    }

    public void setOrganizationDto(OrganizationDto organizationDto) {
        this.organizationDto = organizationDto;
    }

    public String getUserPasswordHash() {
        return userPasswordHash;
    }

    public void setUserPasswordHash(String userPasswordHash) {
        this.userPasswordHash = userPasswordHash;
    }
}