package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 10.01.2017.
 */

@Entity
@Table
public class TerminalSettings implements Serializable {

    public TerminalSettings() {
        Date date = new Date();
        setCityMd(date);
        setAddressMd(date);
        setLatitudeMd(date);
        setLongitudeMd(date);
        setModelMd(date);
        setCaosWWWMd(date);
        setCaosPMMd(date);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String city;
    @Column
    private Date cityMd; // city modify date
    @Column
    private String address;
    @Column
    private Date addressMd; // address modify date
    @Column
    private Double latitude;
    @Column
    private Date latitudeMd; // latitude modify date
    @Column
    private Double longitude;
    @Column
    private Date longitudeMd; // longitude modify date
    @Column
    private String model;
    @Column
    private Date modelMd; // model modify date
    @Column
    private String caosWWWVersion;
    @Column
    private Date caosWWWMd; // CaosWWW version modify date
    @Column
    private String caosPMVersion;
    @Column
    private Date caosPMMd; // CaosPM version modify date

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city, Date modifyDate) {
        if (this.city == null || !this.city.equalsIgnoreCase(city)) {
            this.city = city;
            setCityMd(modifyDate);
        }
    }

    public Date getCityMd() { return cityMd; }
    private void setCityMd(Date cityMd) { this.cityMd = cityMd; }

    public String getAddress() { return address; }
    public void setAddress(String address, Date modifyDate) {
        if (this.address == null || !this.address.equalsIgnoreCase(address)) {
            this.address = address;
            setAddressMd(modifyDate);
        }
    }

    public Date getAddressMd() { return addressMd; }
    private void setAddressMd(Date addressMd) { this.addressMd = addressMd; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude, Date modifyDate) {
        if (this.latitude == null || !this.latitude.equals(latitude)) {
            this.latitude = latitude;
            setLatitudeMd(modifyDate);
        }
    }

    public Date getLatitudeMd() { return latitudeMd; }
    private void setLatitudeMd(Date latitudeMd) { this.latitudeMd = latitudeMd; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude, Date modifyDate) {
        if (this.longitude == null || !this.longitude.equals(longitude)) {
            this.longitude = longitude;
            setLongitudeMd(modifyDate);
        }
    }

    public Date getLongitudeMd() { return longitudeMd; }
    private void setLongitudeMd(Date longitudeMd) { this.longitudeMd = longitudeMd; }

    public String getModel() { return model; }
    public void setModel(String model, Date modifyDate) {
        if (this.model == null || !this.model.equalsIgnoreCase(model)) {
            this.model = model;
            setModelMd(modifyDate);
        }
    }

    public Date getModelMd() { return modelMd; }
    private void setModelMd(Date modelMd) { this.modelMd = modelMd; }

    public String getCaosWWWVersion() { return caosWWWVersion; }
    public boolean setCaosWWWVersion(String caosWWWVersion, Date modifyDate) {
        boolean changed = false;
        if (this.caosWWWVersion == null || !this.caosWWWVersion.equalsIgnoreCase(caosWWWVersion)) {
            this.caosWWWVersion = caosWWWVersion;
            setCaosWWWMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getCaosWWWMd() { return caosWWWMd; }
    private void setCaosWWWMd(Date caosWWWMd) { this.caosWWWMd = caosWWWMd; }

    public String getCaosPMVersion() { return caosPMVersion; }
    public boolean setCaosPMVersion(String caosPMVersion, Date modifyDate) {
        boolean changed = false;
        if (this.caosPMVersion == null || !this.caosPMVersion.equalsIgnoreCase(caosPMVersion)) {
            this.caosPMVersion = caosPMVersion;
            setCaosPMMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getCaosPMMd() { return caosPMMd; }
    private void setCaosPMMd(Date caosPMMd) { this.caosPMMd = caosPMMd; }
}
