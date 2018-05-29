package by.bytechs.service.message.impl;

import by.bytechs.service.TerminalService;
import by.bytechs.service.message.MessageService;
import by.bytechs.service.message.factory.MessageProcessFactory;
import by.bytechs.service.message.factory.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 */

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageProcessFactory messageProcessFactory;
    @Autowired
    private TerminalService terminalService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processIncomingMessage(String message) {
        Document doc = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(message));

            doc = db.parse(inputSource);

            NodeList nodeList = doc.getElementsByTagName("RemoteMessage");
            String deviceID = ((Element) nodeList.item(0)).getAttribute("DeviceID");
            if (!terminalService.isTerminalExists(deviceID)) {
                log.error("Сообщение от неизвестного устройства: " + deviceID);
                return;
            }

            String operation = ((Element) nodeList.item(0)).getAttribute("operation");
            MessageHandler handler = messageProcessFactory.getHandler(operation);
            handler.processMessage(doc);
        } catch (Exception e) {
            log.error("Ошибка парсинга " + e.getMessage() + " " + e.toString());
            e.printStackTrace();
        }
    }
}
