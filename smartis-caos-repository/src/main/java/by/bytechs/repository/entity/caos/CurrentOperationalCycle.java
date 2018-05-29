package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 16.02.2017.
 */

@Entity
@Table(name = "OperationCycles")
public class CurrentOperationalCycle implements Serializable {

    public CurrentOperationalCycle() {}

    public CurrentOperationalCycle(Terminal terminal) {
        setTerminal(terminal);
        setDateStart(new Date());
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private Date dateStart;

    @OneToOne(optional=false, mappedBy="currentOperationalCycle")
    private Terminal terminal;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateStart() { return dateStart; }
    public void setDateStart(Date dateStart) { this.dateStart = dateStart; }

    public Terminal getTerminal() { return terminal; }
    public void setTerminal(Terminal terminal) { this.terminal = terminal; }
}
