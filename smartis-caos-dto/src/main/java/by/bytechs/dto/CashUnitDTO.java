package by.bytechs.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */
public class CashUnitDTO implements Serializable {
    private String currency;
    private int denomination;
    private int quantity;
    private int type;
    private Date modifyDate;

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getDenomination() { return denomination; }
    public void setDenomination(int denomination) { this.denomination = denomination; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    public Date getModifyDate() { return modifyDate; }
    public void setModifyDate(Date modifyDate) { this.modifyDate = modifyDate; }
}
