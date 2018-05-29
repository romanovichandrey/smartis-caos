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
 * Created by Kotsuba_VV on 25.01.2017.
 *
 * <RemoteMessage operation="Vault door open" DeviceID="CDS02456" CustomerCode="" NOP="5289" Date="2017-01-6" Time="09:19:39">
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("DoorOpen")
@Transactional
public class DoorOpenHandler extends MessageHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.DOOR_OPEN);
            log.info("Start DoorOpen message processing for terminal: " + getDeviceID());

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Дверь сейфа открыта");
            terminalLog.setMessageSource(OperationType.RemoteMessage.DOOR_OPEN + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            log.info("End DoorOpen message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
