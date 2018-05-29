package by.bytechs.dto;

import java.io.Serializable;

/**
 * Created by Kotsuba_VV on 10.01.2017.
 */
public class TerminalDTO implements Serializable {
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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTerminalID() { return terminalID; }
    public void setTerminalID(String terminalID) { this.terminalID = terminalID; }

    public String getLogicalName() { return logicalName; }
    public void setLogicalName(String logicalName) { this.logicalName = logicalName; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
