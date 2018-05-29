package by.bytechs.dto.users;

import java.io.Serializable;

/**
 * @author Romanovich Andrei
 */
public class UserKeyDto implements Serializable {
    private String keyId;
    private boolean disabled;
    private Integer accountId;
    private String accountNumber;
    private String accountCurrency;

    public UserKeyDto() {
    }

    public UserKeyDto(String keyId, Integer accountId, String accountNumber, String accountCurrency) {
        this.keyId = keyId;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountCurrency = accountCurrency;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
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
}