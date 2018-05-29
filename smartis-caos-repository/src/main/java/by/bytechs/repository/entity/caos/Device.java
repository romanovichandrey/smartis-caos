package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 16.01.2017.
 */

@Entity
@Table(name = "Devices")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "deviceType", discriminatorType = DiscriminatorType.STRING, length = 255)
public class Device implements Serializable {

    public Device() {
        Date date = new Date();
        setStatusMd(date);
        setStateMd(date);
        setExtCodeMd(date);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int status;
    @Column
    private Date statusMd; // status modify date
    @Column
    private int state;
    @Column
    private Date stateMd; // state modify date
    @Column
    private String extCode;
    @Column
    private Date extCodeMd; // extCode modify date
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "terminalID", nullable = false, referencedColumnName = "id")
    private Terminal terminal;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStatus() { return status; }
    public boolean setStatus(int status, Date modifyDate) {
        boolean changed = false;
        if (this.status != status) {
            this.status = status;
            setStatusMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getStatusMd() { return statusMd; }
    private void setStatusMd(Date statusMd) { this.statusMd = statusMd; }

    public int getState() { return state; }
    public boolean setState(int state, Date modifyDate) {
        boolean changed = false;
        if (this.state != state) {
            this.state = state;
            setStateMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getStateMd() { return stateMd; }
    private void setStateMd(Date stateMd) { this.stateMd = stateMd; }

    public String getExtCode() { return extCode; }
    public boolean setExtCode(String extCode, Date modifyDate) {
        boolean changed = false;
        if (this.extCode == null || !this.extCode.equals(extCode)) {
            this.extCode = extCode;
            setExtCodeMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getExtCodeMd() { return extCodeMd; }
    private void setExtCodeMd(Date extCodeMd) { this.extCodeMd = extCodeMd; }

    public Terminal getTerminal() { return terminal; }
    public void setTerminal(Terminal terminal) { this.terminal = terminal; }
}
