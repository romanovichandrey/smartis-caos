package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.TerminalLog;
import by.bytechs.util.constraints.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 26.01.2017.
 *
 * <RemoteMessage operation="Transport Jam" DeviceID="CDS02456" CustomerCode="" NOP="11392" Date="2017-01-18" Time="11:36:39">
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("TransportJam")
@Transactional
public class TransportJamHandler extends MessageHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.TRANSPORT_JAM);
            log.info("Start TransportJam message processing for terminal: " + getDeviceID());

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Произошло замятие банкнот");
            terminalLog.setMessageSource(OperationType.RemoteMessage.TRANSPORT_JAM + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            log.info("End TransportJam message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
