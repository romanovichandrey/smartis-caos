package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kotsuba_VV on 26.01.2017.
 */

@Entity
@Table(name = "CashUnitHistoryHeaders")
public class CashUnitHistoryHeader {

    public CashUnitHistoryHeader() {
        this.cashUnits = new ArrayList<>();
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Date messageDate;
    @Column(length = 50, nullable = false)
    private String initiator;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "terminalID", nullable = false, referencedColumnName = "id")
    private Terminal terminal;
    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL)
    private List<CashUnitHistory> cashUnits;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getMessageDate() { return messageDate; }
    public void setMessageDate(Date messageDate) { this.messageDate = messageDate; }

    public String getInitiator() { return initiator; }
    public void setInitiator(String initiator) { this.initiator = initiator; }

    public Terminal getTerminal() { return terminal; }
    public void setTerminal(Terminal terminal) { this.terminal = terminal; }

    public List<CashUnitHistory> getCashUnits() { return cashUnits; }
    public void setCashUnits(List<CashUnitHistory> cashUnits) { this.cashUnits = cashUnits; }
}
