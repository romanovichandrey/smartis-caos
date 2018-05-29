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
 * <RemoteMessage operation="Cash bag removed" DeviceID="CDS02456" CustomerCode="" NOP="5290" Date="2017-01-6" Time="09:19:40">
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("CashBagRemoved")
@Transactional
public class CashBagRemovedHandler extends MessageHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.CASH_BAG_REMOVED);

            log.info("Start CashBagRemoved message processing for terminal: " + getDeviceID());

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Мешок извлечен");
            terminalLog.setMessageSource(OperationType.RemoteMessage.CASH_BAG_REMOVED + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            log.info("End CashBagRemoved message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
