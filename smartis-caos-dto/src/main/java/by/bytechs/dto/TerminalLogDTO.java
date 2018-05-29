package by.bytechs.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 14.02.2017.
 */
public class TerminalLogDTO implements Serializable {
    private long id;
    private String terminalID;
    private String message;
    private String messageSource;
    private Date terminalDate;
    private Date serverDate;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTerminalID() { return terminalID; }
    public void setTerminalID(String terminalID) { this.terminalID = terminalID; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getMessageSource() { return messageSource; }
    public void setMessageSource(String messageSource) { this.messageSource = messageSource; }

    public Date getTerminalDate() { return terminalDate; }
    public void setTerminalDate(Date terminalDate) { this.terminalDate = terminalDate; }

    public Date getServerDate() { return serverDate; }
    public void setServerDate(Date serverDate) { this.serverDate = serverDate; }
}
