package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.Terminal;
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

/**
 * Created by Kotsuba_VV on 25.01.2017.
 *
 <RemoteMessage operation="Component fail" DeviceID="CDS02456" CustomerCode="" NOP="11172" Date="2017-01-10" Time="10:28:31">
    <devStatus>
        <dev name="BDM" mod="CDS 707C" err="OK" clean="0" door="1" devS="1" bag="0" cov="0" blk="00000000" bund="-2" ext="10049 "/> -- code 10049 - safe door is open
        <dev name="IBM" err="OK" devS="1" ext="0 "/>
        <dev name="PTR" err="OK" devS="1" ext="0000 "/>
    </devStatus>
 </RemoteMessage>

 <RemoteMessage operation="Component fail" DeviceID="CDS02456" CustomerCode="" NOP="11394" Date="2017-01-18" Time="11:37:09">
    <devStatus>
        <dev name="BDM" mod="CDS 707C" err="KO" clean="0" door="0" devS="3" bag="2" cov="0" blk="00000000" bund="-2" ext="10013 CODE: 1A1 AREA: 1 D1: 0 BOARD: 7"/>  -- code 10013 - transport jam
        <dev name="IBM" err="OK" devS="1" ext="0 "/>
        <dev name="PTR" err="OK" devS="1" ext="0000 "/>
    </devStatus>
 </RemoteMessage>
 *
 */

@Service
@Qualifier("ComponentFail")
@Transactional
public class ComponentFailHandler extends MessageHandler {
    @Autowired
    private TerminalService terminalService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.COMPONENT_FAIL);

            log.info("Start ComponentFail message processing for terminal: " + getDeviceID());

            NodeList deviceList = document.getElementsByTagName("dev");
            Terminal terminal = terminalService.findByTerminalID(getDeviceID());

            updateDevices(deviceList, terminal);

            terminalService.saveTerminal(terminal);

            log.info("End ComponentFail message processing for terminal: " + getDeviceID());
        } catch (Exception e) {
            printStackTrace(e, log);
        }
    }
}
