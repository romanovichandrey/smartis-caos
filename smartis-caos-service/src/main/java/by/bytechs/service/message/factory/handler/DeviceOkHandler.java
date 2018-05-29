package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.*;
import by.bytechs.service.CashUnitHistoryService;
import by.bytechs.service.CashUnitService;
import by.bytechs.service.TerminalService;
import by.bytechs.util.constraints.ApplicationStatus;
import by.bytechs.util.constraints.DeviceStatusConstraints;
import by.bytechs.util.constraints.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 *
 * <RemoteMessage operation="Device OK" DeviceID="CDS02456" CustomerCode="" NOP="11607" Date="2017-01-20" Time="15:39:03">
 *     <content>
 *         <count den="1000" curr="BYR" qty="1" type="B" N="0" sType="B"/>
 *     </content>
 *     <devStatus>
 *         <dev name="BDM" mod="CDS 707C" err="OK" clean="0" door="0" devS="1" bag="2" cov="0" blk="00000000" bund="-2" ext="0 "/>
 *         <dev name="IBM" err="OK" devS="1" ext="0 "/>
 *         <dev name="PTR" err="OK" devS="1" ext="0000 "/>
 *     </devStatus>
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("DeviceOK")
@Transactional
public class DeviceOkHandler extends MessageHandler {
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private CashUnitService cashUnitService;
    @Autowired
    private CashUnitHistoryService cashUnitHistoryService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.DEVICE_OK);

            log.info("Start DeviceOK message processing for terminal: " + getDeviceID());

            NodeList cashUnitList = document.getElementsByTagName("count");
            NodeList deviceList = document.getElementsByTagName("dev");
            Terminal terminal = terminalService.findByTerminalID(getDeviceID());

            updateDevices(deviceList, terminal);

            terminal.setAppState(ApplicationStatus.GOOD, getDateTime());
            if (terminal.isOnlineStatus()) {
                terminal.setOverallStatus(ApplicationStatus.GOOD, getDateTime());
            }

            if (cashUnitList != null && cashUnitList.getLength() > 0) {
                List<CashUnit> newCashUnits = new ArrayList<>();
                for (int i = 0; i < cashUnitList.getLength(); i++) {
                    Node cashUnitNode = cashUnitList.item(i);
                    String currency = cashUnitNode.getAttributes().getNamedItem("curr").getNodeValue();
                    int denomination = Integer.parseInt(cashUnitNode.getAttributes().getNamedItem("den").getNodeValue().trim());
                    int quantity = Integer.parseInt(cashUnitNode.getAttributes().getNamedItem("qty").getNodeValue().trim());
                    int cashType = cashUnitNode.getAttributes().getNamedItem("type").getNodeValue().equals("B") ?
                            DeviceStatusConstraints.CashType.BANKNOTE : DeviceStatusConstraints.CashType.COIN;
                    CashUnit cashUnit = new CashUnit();
                    cashUnit.setCurrency(currency);
                    cashUnit.setDenomination(denomination);
                    cashUnit.setQuantity(quantity);
                    cashUnit.setType(cashType);
                    cashUnit.setModifyDate(getDateTime());
                    newCashUnits.add(cashUnit);
                }
                updateCashUnits(terminal, newCashUnits, getDateTime());
            }

            terminalService.saveTerminal(terminal);

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Устройство в порядке");
            terminalLog.setMessageSource(OperationType.RemoteMessage.DEVICE_OK + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            log.info("End DeviceOK message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }



    private Terminal updateCashUnits(Terminal terminal, List<CashUnit> cashUnits, Date modifyDate) {
        List<CashUnitHistory> cashUnitHistoryList = cashUnitHistoryService.convertCashUnitsToHistory(cashUnits);
        CashUnitHistoryHeader header = new CashUnitHistoryHeader();
        header.setTerminal(terminal);
        header.setMessageDate(modifyDate);
        header.setInitiator("Incoming DeviceOK Message");
        header.setCashUnits(cashUnitHistoryList);
        cashUnitHistoryList.stream().forEach(cashUnitHistory -> cashUnitHistory.setHeader(header));
        cashUnitHistoryService.saveHistory(header);

        BanknoteModule module = (BanknoteModule) terminal.getDevices().stream().filter(device -> device instanceof BanknoteModule).findFirst().get();
        module.getCurrentCashUnits().clear();
        cashUnitService.deleteAllByBDM(module);
        module.getCurrentCashUnits().addAll(cashUnits);
        cashUnits.stream().forEach(cashUnit -> cashUnit.setBanknoteModule(module));

        return terminal;
    }
}
