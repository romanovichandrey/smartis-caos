package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kotsuba_VV on 10.01.2017.
 */

@Entity
@Table(name = "Terminals")
public class Terminal implements Serializable {

    public Terminal() {
        TerminalSettings terminalSettings = new TerminalSettings();
        this.setTerminalSettings(terminalSettings);
        this.setDevices(new ArrayList<>());
        this.setCurrentOperationalCycle(new CurrentOperationalCycle(this));
        Date date = new Date();
        setTerminalIDMd(date);
        setLogicalNameMd(date);
        setHostMd(date);
        setPortMd(date);
        setOnlineStatusMd(date);
        setAppStateMd(date);
        setOverallStatusMd(date);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false, length = 8)
    private String terminalID;
    @Column
    private Date terminalIDMd; // terminalID modify date
    @Column(unique = true, nullable = false)
    private String logicalName;
    @Column
    private Date logicalNameMd; // logical name modify date
    @Column(unique = true, nullable = false, length = 15)
    private String host;
    @Column
    private Date hostMd; // host modify date
    @Column(length = 5)
    private Integer port;
    @Column
    private Date portMd; // port modify date
    @Column
    private Boolean onlineStatus;
    @Column
    private Date onlineStatusMd; // online status modify date
    @Column
    private Boolean appState;
    @Column
    private Date appStateMd; // app state modify date
    @Column
    private Boolean overallStatus;
    @Column
    private Date overallStatusMd; // overall status modify date

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "settingsID")
    private TerminalSettings terminalSettings;
    @OneToMany(mappedBy = "terminal", cascade = CascadeType.ALL)
    private List<Device> devices;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="cycleID")
    private CurrentOperationalCycle currentOperationalCycle;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTerminalID() { return terminalID; }
    public void setTerminalID(String terminalID, Date modifyDate) {
        if (this.terminalID == null || !this.terminalID.equals(terminalID)) {
            this.terminalID = terminalID;
            setTerminalIDMd(modifyDate);
        }
    }

    public Date getTerminalIDMd() { return terminalIDMd; }
    private void setTerminalIDMd(Date terminalIDMd) { this.terminalIDMd = terminalIDMd; }

    public String getLogicalName() { return logicalName; }
    public void setLogicalName(String logicalName, Date modifyDate) {
        if (this.logicalName == null || !this.logicalName.equals(logicalName)) {
            this.logicalName = logicalName;
            setLogicalNameMd(modifyDate);
        }
    }

    public Date getLogicalNameMd() { return logicalNameMd; }
    private void setLogicalNameMd(Date logicalNameMd) { this.logicalNameMd = logicalNameMd; }

    public String getHost() { return host; }
    public void setHost(String host, Date modifyDate) {
        if (this.host == null || !this.host.equals(host)) {
            this.host = host;
            setHostMd(modifyDate);
        }
    }

    public Date getHostMd() { return hostMd; }
    private void setHostMd(Date hostMd) { this.hostMd = hostMd; }

    public Integer getPort() { return port; }
    public void setPort(Integer port, Date modifyDate) {
        if (this.port == null || !this.port.equals(port)) {
            this.port = port;
            setPortMd(modifyDate);
        }
    }

    public Date getPortMd() { return portMd; }
    private void setPortMd(Date portMd) { this.portMd = portMd; }

    public boolean isOnlineStatus() { return onlineStatus != null ? onlineStatus.booleanValue() : false; }
    public void setOnlineStatus(boolean onlineStatus, Date modifyDate) {
        if (this.onlineStatus == null || this.onlineStatus != onlineStatus) {
            this.onlineStatus = onlineStatus;
            setOnlineStatusMd(modifyDate);
        }
    }

    public Date getOnlineStatusMd() { return onlineStatusMd; }
    private void setOnlineStatusMd(Date onlineStatusMd) { this.onlineStatusMd = onlineStatusMd; }

    public boolean isAppState() { return appState != null ? appState.booleanValue() : false; }
    public void setAppState(boolean appState, Date modifyDate) {
        if (this.appState == null || this.appState != appState) {
            this.appState = appState;
            setAppStateMd(modifyDate);
        }
    }

    public Date getAppStateMd() { return appStateMd; }
    private void setAppStateMd(Date appStateMd) { this.appStateMd = appStateMd; }

    public boolean isOverallStatus() { return overallStatus != null ? overallStatus.booleanValue() : false; }
    public void setOverallStatus(boolean overallStatus, Date modifyDate) {
        if (this.overallStatus == null || this.overallStatus != overallStatus) {
            this.overallStatus = overallStatus;
            setOverallStatusMd(modifyDate);
        }
    }

    public Date getOverallStatusMd() { return overallStatusMd; }
    private void setOverallStatusMd(Date overallStatusMd) { this.overallStatusMd = overallStatusMd; }

    public TerminalSettings getTerminalSettings() { return terminalSettings; }
    public void setTerminalSettings(TerminalSettings terminalSettings) { this.terminalSettings = terminalSettings; }

    public List<Device> getDevices() { return devices; }
    public void setDevices(List<Device> devices) { this.devices = devices; }

    public CurrentOperationalCycle getCurrentOperationalCycle() { return currentOperationalCycle; }
    public void setCurrentOperationalCycle(CurrentOperationalCycle currentOperationalCycle) { this.currentOperationalCycle = currentOperationalCycle; }

    @Override
    public String toString() {
        return "Terminal: [id: " + this.getId() + "terminalID]";
    }
}
