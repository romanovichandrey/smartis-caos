package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.repository.entity.caos.TerminalLog;
import by.bytechs.repository.entity.caos.TerminalSettings;
import by.bytechs.service.TerminalService;
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

import java.util.Date;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 *
 * <RemoteMessage operation="DevInfo" DeviceID="CDS02456" CustomerCode="" NOP="11202" Date="2017-01-10" Time="10:37:35">
 *     <software>
 *         <sw name="CaosWWW" version="5.8.0"/>
 *         <sw name="CaosPM" version="5.8.0"/>
 *     </software>
 *     <BDM>
 *         <fw version="2.90"/>
 *         <RecFw version="017-BY-00/017(017) SW:03.D9 S/N:154787892 #1"/>
 *         <model name="CDS 707C"/>
 *         <stocksConfig>
 *             <stock id="0" type="bag" recycle="false">
 *                 <denom curr="BYR" value="50"/>
 *                 <denom curr="BYR" value="100"/>
 *             </stock>
 *         </stocksConfig>
 *     </BDM>
 * </RemoteMessage>
 *
 */

@Service
@Qualifier("DevInfo")
@Transactional
public class DevInfoHandler extends MessageHandler {
    @Autowired
    private TerminalService terminalService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.DEV_INFO);

            log.info("Start DevInfo message processing for terminal: " + getDeviceID());

            boolean isCaosWWWChanged = false;
            boolean isCaosPMChanged = false;

            Terminal terminal = terminalService.findByTerminalID(getDeviceID());
            TerminalSettings terminalSettings = terminal.getTerminalSettings();
            if (terminalSettings == null) {
                terminalSettings = new TerminalSettings();
                terminal.setTerminalSettings(terminalSettings);
            }

            NodeList softVersionNodeList = document.getElementsByTagName("sw");
            if (softVersionNodeList != null && softVersionNodeList.getLength() > 0) {
                for (int i = 0; i < softVersionNodeList.getLength(); i++) {
                    Node softVersionNode = softVersionNodeList.item(i);
                    String softType = softVersionNode.getAttributes().getNamedItem("name").getNodeValue();
                    String version = softVersionNode.getAttributes().getNamedItem("version").getNodeValue();
                    if (softType.equals("CaosWWW")) {
                        isCaosWWWChanged = terminal.getTerminalSettings().setCaosWWWVersion(version, getDateTime());
                    } else if (softType.equals("CaosPM")) {
                        isCaosPMChanged = terminal.getTerminalSettings().setCaosPMVersion(version, getDateTime());
                    }
                }
            }

            terminalService.saveTerminal(terminal);

            if (isCaosWWWChanged) {
                TerminalLog terminalLog = new TerminalLog();
                terminalLog.setTerminalID(getDeviceID());
                terminalLog.setMessage("Версия CaosWWW изменилась на " + terminal.getTerminalSettings().getCaosWWWVersion());
                terminalLog.setMessageSource(OperationType.RemoteMessage.DEV_INFO + " message");
                terminalLog.setTerminalDate(getDateTime());
                terminalLog.setServerDate(new Date());
                getLogService().saveLog(terminalLog);
            }

            if (isCaosPMChanged) {
                TerminalLog terminalLog = new TerminalLog();
                terminalLog.setTerminalID(getDeviceID());
                terminalLog.setMessage("Версия CaosPM изменилась на " + terminal.getTerminalSettings().getCaosPMVersion());
                terminalLog.setMessageSource(OperationType.RemoteMessage.DEV_INFO + " message");
                terminalLog.setTerminalDate(getDateTime());
                terminalLog.setServerDate(new Date());
                getLogService().saveLog(terminalLog);
            }

            log.info("End DevInfo message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
