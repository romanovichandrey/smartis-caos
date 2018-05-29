package by.bytechs.repository.entity.caos;

import javax.persistence.*;

/**
 * Created by Kotsuba_VV on 26.01.2017.
 */

@Entity
@Table(name = "CashUnitHistory")
public class CashUnitHistory {
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "headerID", nullable = false, referencedColumnName = "id")
    private CashUnitHistoryHeader header;

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

    public CashUnitHistoryHeader getHeader() { return header; }
    public void setHeader(CashUnitHistoryHeader header) { this.header = header; }
}
