package by.bytechs.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Kotsuba_VV on 18.01.2017.
 */
public class TerminalInfoDTO implements Serializable {

    public TerminalInfoDTO() {
        this.setBanknoteModule(new BanknoteModuleDTO());
        this.setiButtonReader(new IButtonReaderDTO());
        this.setPrinter(new PrinterDTO());
    }

    private int id;
    private String terminalID;
    private String logicalName;
    private String host;
    private int port;
    private String city;
    private String address;
    private Double latitude;
    private Double longitude;
    private String model;
    private String caosWWWVersion;
    private String caosPMVersion;
    private BanknoteModuleDTO banknoteModule;
    private IButtonReaderDTO iButtonReader;
    private PrinterDTO printer;
    private Date lastPing;
    private List<TerminalLogDTO> lastMessages;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTerminalID() { return terminalID; }
    public void setTerminalID(String terminalID) { this.terminalID = terminalID; }

    public String getLogicalName() { return logicalName; }
    public void setLogicalName(String logicalName) { this.logicalName = logicalName; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getCaosWWWVersion() { return caosWWWVersion; }
    public void setCaosWWWVersion(String caosWWWVersion) { this.caosWWWVersion = caosWWWVersion; }

    public String getCaosPMVersion() { return caosPMVersion; }
    public void setCaosPMVersion(String caosPMVersion) { this.caosPMVersion = caosPMVersion; }

    public BanknoteModuleDTO getBanknoteModule() { return banknoteModule; }
    public void setBanknoteModule(BanknoteModuleDTO banknoteModule) { this.banknoteModule = banknoteModule; }

    public IButtonReaderDTO getiButtonReader() { return iButtonReader; }
    public void setiButtonReader(IButtonReaderDTO iButtonReader) { this.iButtonReader = iButtonReader; }

    public PrinterDTO getPrinter() { return printer; }
    public void setPrinter(PrinterDTO printer) { this.printer = printer; }

    public Date getLastPing() { return lastPing; }
    public void setLastPing(Date lastPing) { this.lastPing = lastPing; }

    public List<TerminalLogDTO> getLastMessages() { return lastMessages; }
    public void setLastMessages(List<TerminalLogDTO> lastMessages) { this.lastMessages = lastMessages; }
}
