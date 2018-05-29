package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kotsuba_VV on 16.01.2017.
 */

@Entity
@Table(name = "BanknoteModules")
public class BanknoteModule extends Device {

    public BanknoteModule() {
        super();
        this.setCurrentCashUnits(new ArrayList<>());
        Date date = new Date();
        setCleaningMd(date);
        setDoorMd(date);
        setBagStatusMd(date);
        setBundlerStatusMd(date);
        setCoverMd(date);
    }

    @Column
    private int cleaning;
    @Column
    private Date cleaningMd; // cleaning modify date
    @Column
    private int door;
    @Column
    private Date doorMd; // door modify date
    @Column
    private int bagStatus;
    @Column
    private Date bagStatusMd; // bag status modify date
    @Column
    private int bundlerStatus;
    @Column
    private Date bundlerStatusMd; // bundler status modify date
    @Column
    private int cover;
    @Column
    private Date coverMd; // cover modify date
    @OneToMany(mappedBy = "banknoteModule", cascade = CascadeType.ALL)
    private List<CashUnit> currentCashUnits;

    public int getCleaning() { return cleaning; }
    public boolean setCleaning(int cleaning, Date modifyDate) {
        boolean changed = false;
        if (this.cleaning != cleaning) {
            this.cleaning = cleaning;
            setCleaningMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getCleaningMd() { return cleaningMd; }
    private void setCleaningMd(Date cleaningMd) { this.cleaningMd = cleaningMd; }

    public int getDoor() { return door; }
    public boolean setDoor(int door, Date modifyDate) {
        boolean changed = false;
        if (this.door != door) {
            this.door = door;
            setDoorMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getDoorMd() { return doorMd; }
    private void setDoorMd(Date doorMd) { this.doorMd = doorMd; }

    public int getBagStatus() { return bagStatus; }
    public boolean setBagStatus(int bagStatus, Date modifyDate) {
        boolean changed = false;
        if (this.bagStatus != bagStatus) {
            this.bagStatus = bagStatus;
            setBagStatusMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getBagStatusMd() { return bagStatusMd; }
    private void setBagStatusMd(Date bagStatusMd) { this.bagStatusMd = bagStatusMd; }

    public int getBundlerStatus() { return bundlerStatus; }
    public boolean setBundlerStatus(int bundlerStatus, Date modifyDate) {
        boolean changed = false;
        if (this.bundlerStatus != bundlerStatus) {
            this.bundlerStatus = bundlerStatus;
            setBundlerStatusMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getBundlerStatusMd() { return bundlerStatusMd; }
    private void setBundlerStatusMd(Date bundlerStatusMd) { this.bundlerStatusMd = bundlerStatusMd; }

    public int getCover() { return cover; }
    public boolean setCover(int cover, Date modifyDate) {
        boolean changed = false;
        if (this.cover != cover) {
            this.cover = cover;
            setCoverMd(modifyDate);
            changed = true;
        }
        return changed;
    }

    public Date getCoverMd() { return coverMd; }
    private void setCoverMd(Date coverMd) { this.coverMd = coverMd; }

    public List<CashUnit> getCurrentCashUnits() { return currentCashUnits; }
    public void setCurrentCashUnits(List<CashUnit> currentCashUnits) { this.currentCashUnits = currentCashUnits; }
}
