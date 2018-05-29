package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */

@Entity
@Table(name = "Transactions")
public class Transaction implements Serializable {
    @Id
    @Column
    private long transactionID;
    @Column
    private int transactionType;
    @Column
    private Date transactionDate;
    @Column
    private String transactionUser;
    @Column
    private String userAlternateID;
    @Column
    private String userOrganization;
    @Column
    private String userInfo;
    @Column
    private String userLevel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "headerID")
    private CashUnitHistoryHeader header;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "terminalID")
    private Terminal terminal;

    public long getTransactionID() { return transactionID; }
    public void setTransactionID(long transactionID) { this.transactionID = transactionID; }

    public int getTransactionType() { return transactionType; }
    public void setTransactionType(int transactionType) { this.transactionType = transactionType; }

    public Date getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }

    public String getTransactionUser() { return transactionUser; }
    public void setTransactionUser(String transactionUser) { this.transactionUser = transactionUser; }

    public String getUserAlternateID() { return userAlternateID; }
    public void setUserAlternateID(String userAlternateID) { this.userAlternateID = userAlternateID; }

    public String getUserOrganization() { return userOrganization; }
    public void setUserOrganization(String userOrganization) { this.userOrganization = userOrganization; }

    public String getUserInfo() { return userInfo; }
    public void setUserInfo(String userInfo) { this.userInfo = userInfo; }

    public String getUserLevel() { return userLevel; }
    public void setUserLevel(String userLevel) { this.userLevel = userLevel; }

    public CashUnitHistoryHeader getHeader() { return header; }
    public void setHeader(CashUnitHistoryHeader header) { this.header = header; }

    public Terminal getTerminal() { return terminal; }
    public void setTerminal(Terminal terminal) { this.terminal = terminal; }
}
