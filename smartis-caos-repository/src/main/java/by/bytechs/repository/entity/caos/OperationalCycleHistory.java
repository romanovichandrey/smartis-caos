package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */

@Entity
@Table
public class OperationalCycleHistory implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Date dateStart;
    @Column
    private Date dateEnd;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "headerID")
    private CashUnitHistoryHeader header;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "terminalID", nullable = false, referencedColumnName = "id")
    private Terminal terminal;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateStart() { return dateStart; }
    public void setDateStart(Date dateStart) { this.dateStart = dateStart; }

    public Date getDateEnd() { return dateEnd; }
    public void setDateEnd(Date dateEnd) { this.dateEnd = dateEnd; }

    public CashUnitHistoryHeader getHeader() { return header; }
    public void setHeader(CashUnitHistoryHeader header) { this.header = header; }

    public Terminal getTerminal() { return terminal; }
    public void setTerminal(Terminal terminal) { this.terminal = terminal; }
}
