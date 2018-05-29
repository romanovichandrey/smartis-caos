package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.repository.entity.caos.TerminalLog;
import by.bytechs.service.CashUnitHistoryService;
import by.bytechs.service.CashUnitService;
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
import org.w3c.dom.NodeList;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 *
 * <RemoteMessage operation="Cash removal" DeviceID="CDS02456" CustomerCode="" NOP="11399" Date="2017-01-18" Time="11:52:47">
 *     <User>1</User>
 *     <UserAlternateID/>
 *     <TransactionID>152</TransactionID>
 *     <UserLevel>1</UserLevel>
 *     <bagID></bagID>
 *     <Conciliation/>
 *     <Details Currency="BYR">
 *         <countings valid="3">
 *             <counted denom="50" quantity="12" type="B"/>
 *             <counted denom="100" quantity="1" type="B"/>
 *             <counted denom="1000" quantity="1" type="B"/>
 *         </countings>
 *         <Account/>
 *         <total>1700</total>
 *     </Details>
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("CashRemoval")
@Transactional
public class CashRemovalHandler extends MessageHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TerminalService terminalService;
    @Autowired
    private CashUnitService cashUnitService;
    @Autowired
    private CashUnitHistoryService cashUnitHistoryService;
    @Autowired
    private OperationalCycleService operationalCycleService;

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.CASH_REMOVAL);

            log.info("Start CashRemoval message processing for terminal: " + getDeviceID());

            Terminal terminal = terminalService.findByTerminalID(getDeviceID());

            NodeList detailsNodeList = document.getElementsByTagName("Details");

            CashUnitHistoryHeader header = getCashStructure(detailsNodeList, terminal);

            log.info("CashRemoval before save history");

            cashUnitHistoryService.saveHistory(header);

            log.info("CashRemoval after save history");

            // удаляем текущие счетчики банкнот, мешок пуст
            BanknoteModule module = (BanknoteModule) terminal.getDevices().stream().filter(device -> device instanceof BanknoteModule).findFirst().get();
            module.getCurrentCashUnits().clear();
            cashUnitService.deleteAllByBDM(module);

            log.info("CashRemoval after clean");

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Оперцикл закрыт. Мешок пуст");
            terminalLog.setMessageSource(OperationType.RemoteMessage.CASH_REMOVAL + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            // закрываем оперцикл
            operationalCycleService.closeOperationalCycle(terminal, getDateTime(), header);

            log.info("End CashRemoval message processing for terminal: " + getDeviceID());
        } catch (Exception ex) {
            printStackTrace(ex, log);
        }
    }
}
