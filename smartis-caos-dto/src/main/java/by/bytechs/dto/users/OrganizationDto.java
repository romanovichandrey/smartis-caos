package by.bytechs.dto.users;

import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
public class OrganizationDto implements Serializable {
    private Integer id;
    private String legalAddress;
    private String name;
    private String phoneNumber;
    private List<AccountDto> accountDtoList;

    public OrganizationDto() {
    }

    public OrganizationDto(Integer id, String legalAddress, String name, String phoneNumber) {
        this.id = id;
        this.legalAddress = legalAddress;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<AccountDto> getAccountDtoList() {
        return accountDtoList;
    }

    public void setAccountDtoList(List<AccountDto> accountDtoList) {
        this.accountDtoList = accountDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationDto that = (OrganizationDto) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getLegalAddress() != null ? !getLegalAddress().equals(that.getLegalAddress()) : that.getLegalAddress() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getPhoneNumber() != null ? getPhoneNumber().equals(that.getPhoneNumber()) : that.getPhoneNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getLegalAddress() != null ? getLegalAddress().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
