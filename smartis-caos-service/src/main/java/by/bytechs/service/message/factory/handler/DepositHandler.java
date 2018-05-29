package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.CashUnitHistoryHeader;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.repository.entity.caos.TerminalLog;
import by.bytechs.repository.entity.caos.Transaction;
import by.bytechs.service.CashUnitHistoryService;
import by.bytechs.service.TerminalService;
import by.bytechs.service.TransactionService;
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
 * <RemoteMessage operation="Deposit" DeviceID="CDS02456" CustomerCode="" NOP="11393" Date="2017-01-18" Time="11:37:02">
 *     <User>2</User>
 *     <UserAlternateID>2b</UserAlternateID>
 *     <UserOrganization>ООО </UserOrganization>
 *     <TransactionID>151</TransactionID>
 *     <UserInfo>123456789</UserInfo>
 *     <UserLevel>0</UserLevel>
 *     <ChannelID>0</ChannelID>
 *     <shiftNumber/>
 *     <DepositDuration>108</DepositDuration>
 *     <shiftEnd>0</shiftEnd>
 *     <bagID></bagID>
 *     <Conciliation>1</Conciliation>
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
@Qualifier("Deposit")
@Transactional
public class DepositHandler extends MessageHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TerminalService terminalService;
    @Autowired
    private CashUnitHistoryService cashUnitHistoryService;
    @Autowired
    private TransactionService transactionService;

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.DEPOSIT);
            log.info("Start Deposit message processing for terminal: " + getDeviceID());

            Terminal terminal = terminalService.findByTerminalID(getDeviceID());

            String user = "";
            String userAlternateID = "";
            String userOrganization = "";
            String userInfo = "";
            String userLevel = "";
            long transactionID = -1l;
            if (document.getElementsByTagName("User") != null) {
                if (document.getElementsByTagName("User").item(0) != null) {
                    user = document.getElementsByTagName("User").item(0).getTextContent();
                }
            }
            if (document.getElementsByTagName("UserAlternateID") != null) {
                if (document.getElementsByTagName("UserAlternateID").item(0) != null) {
                    userAlternateID = document.getElementsByTagName("UserAlternateID").item(0).getTextContent();
                }
            }
            if (document.getElementsByTagName("UserOrganization") != null) {
                if (document.getElementsByTagName("UserOrganization").item(0) != null) {
                    userOrganization = document.getElementsByTagName("UserOrganization").item(0).getTextContent();
                }
            }
            if (document.getElementsByTagName("UserInfo") != null) {
                if (document.getElementsByTagName("UserInfo").item(0) != null) {
                    userInfo = document.getElementsByTagName("UserInfo").item(0).getTextContent();
                }
            }
            if (document.getElementsByTagName("UserLevel") != null) {
                if (document.getElementsByTagName("UserLevel").item(0) != null) {
                    userLevel = document.getElementsByTagName("UserLevel").item(0).getTextContent();
                }
            }
            if (document.getElementsByTagName("TransactionID") != null) {
                if (document.getElementsByTagName("TransactionID").item(0) != null) {
                    transactionID = Long.parseLong(document.getElementsByTagName("TransactionID").item(0).getTextContent());
                }
            }
            NodeList detailsNodeList = document.getElementsByTagName("Details");

            CashUnitHistoryHeader header = getCashStructure(detailsNodeList, terminal);

            cashUnitHistoryService.saveHistory(header);

            Transaction transaction = new Transaction();
            transaction.setTransactionID(transactionID);
            transaction.setTransactionType(OperationType.TransactionType.DEPOSIT);
            transaction.setTransactionDate(getDateTime());
            transaction.setTerminal(terminal);
            transaction.setTransactionUser(user);
            transaction.setUserAlternateID(userAlternateID);
            transaction.setUserOrganization(userOrganization);
            transaction.setUserInfo(userInfo);
            transaction.setUserLevel(userLevel);
            transaction.setHeader(header);

            transactionService.saveTransaction(transaction);

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Произведена депозитная операция");
            terminalLog.setMessageSource(OperationType.RemoteMessage.DEPOSIT + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            log.info("End Deposit message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
