package by.bytechs.dto.users;

import java.io.Serializable;
import java.util.List;

/**
 * @author Romanovich Andrei
 */
public class AccountDto implements Serializable {
    private Integer id;
    private String accountNumber;
    private String accountCurrency;
    private List<UserKeyDto> userKeyDtoList;

    public AccountDto() {
    }

    public AccountDto(Integer id, String accountNumber, String accountCurrency) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountCurrency = accountCurrency;
    }

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

    public List<UserKeyDto> getUserKeyDtoList() {
        return userKeyDtoList;
    }

    public void setUserKeyDtoList(List<UserKeyDto> userKeyDtoList) {
        this.userKeyDtoList = userKeyDtoList;
    }

    @Override
    public String toString() {
        return accountNumber;
    }
}