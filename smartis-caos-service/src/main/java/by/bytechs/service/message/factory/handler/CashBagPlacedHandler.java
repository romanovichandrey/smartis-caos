package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.CurrentOperationalCycle;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.repository.entity.caos.TerminalLog;
import by.bytechs.service.OperationalCycleService;
import by.bytechs.service.TerminalService;
import by.bytechs.util.constraints.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 *
 * <RemoteMessage operation="Cash bag placed" DeviceID="CDS02456" CustomerCode="" NOP="5387" Date="2017-01-6" Time="09:21:22">
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("CashBagPlaced")
@Transactional
public class CashBagPlacedHandler extends MessageHandler {
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private OperationalCycleService operationalCycleService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.CASH_BAG_PLACED);

            log.info("Start CashBagPlaced message processing for terminal: " + getDeviceID());

            Terminal terminal = terminalService.findByTerminalID(getDeviceID());

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Мешок вставлен. Производится открытие оперцикла");
            terminalLog.setMessageSource(OperationType.RemoteMessage.CASH_BAG_PLACED + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            // открытие нового оперцикла
            CurrentOperationalCycle cycle = operationalCycleService.openOperationalCycle(terminal, getDateTime());
            terminal.setCurrentOperationalCycle(cycle);

            terminalService.saveTerminal(terminal);

            log.info("End CashBagPlaced message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
