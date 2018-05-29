package by.bytechs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */

public class BanknoteModuleDTO implements Serializable {

    public BanknoteModuleDTO() {
        this.setCashUnits(new ArrayList<>());
    }

    private int id;

    private int status;

    private int state;
    private int cleaning;
    private int door;
    private int bagStatus;
    private int bundlerStatus;
    private int cover;
    private List<CashUnitDTO> cashUnits;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getState() { return state; }
    public void setState(int state) { this.state = state; }

    public int getCleaning() { return cleaning; }
    public void setCleaning(int cleaning) { this.cleaning = cleaning; }

    public int getDoor() { return door; }
    public void setDoor(int door) { this.door = door; }

    public int getBagStatus() { return bagStatus; }
    public void setBagStatus(int bagStatus) { this.bagStatus = bagStatus; }

    public int getBundlerStatus() { return bundlerStatus; }
    public void setBundlerStatus(int bundlerStatus) { this.bundlerStatus = bundlerStatus; }

    public int getCover() { return cover; }
    public void setCover(int cover) { this.cover = cover; }

    public List<CashUnitDTO> getCashUnits() { return cashUnits; }
    public void setCashUnits(List<CashUnitDTO> cashUnits) { this.cashUnits = cashUnits; }
}
