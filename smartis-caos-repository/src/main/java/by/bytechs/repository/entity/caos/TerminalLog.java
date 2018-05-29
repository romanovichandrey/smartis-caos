package by.bytechs.repository.entity.caos;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 13.02.2017.
 */

@Entity
@Table(name = "TerminalLogs")
public class TerminalLog {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 8, nullable = false)
    private String terminalID;
    @Column
    private String message;
    @Column
    private String messageSource;
    @Column
    private Date terminalDate;
    @Column
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
