package by.bytechs.dto;

import java.io.Serializable;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */
public class PrinterDTO implements Serializable {
    private int id;
    private int status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
