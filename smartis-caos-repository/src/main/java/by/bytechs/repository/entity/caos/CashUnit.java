package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 18.01.2017.
 */

@Entity
@Table(name = "CashUnits")
public class CashUnit implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 3)
    private String currency;
    @Column(nullable = false)
    private int denomination;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private Date modifyDate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "banknoteModuleID", nullable = false, referencedColumnName = "id")
    private BanknoteModule banknoteModule;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getDenomination() { return denomination; }
    public void setDenomination(int denomination) { this.denomination = denomination; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }

    public BanknoteModule getBanknoteModule() { return banknoteModule; }
    public void setBanknoteModule(BanknoteModule banknoteModule) { this.banknoteModule = banknoteModule; }
}
