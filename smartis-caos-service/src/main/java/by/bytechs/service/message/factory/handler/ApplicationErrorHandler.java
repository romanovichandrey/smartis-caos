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
 * <RemoteMessage operation="Application Error" DeviceID="12345678" CustomerCode="" NOP="615" Date="2011-04-19" Time="11:05:55">
       <ErrCode>504</ErrCode>
   </RemoteMessage>
 *
 */

@Service
@Qualifier("ApplicationError")
@Transactional
public class ApplicationErrorHandler extends MessageHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void processMessage(Document document) {
        try {
            init(document, OperationType.RemoteMessage.APPLICATION_ERROR);

            log.info("Start ApplicationError message processing for terminal: " + getDeviceID());

            String errorCode = "";
            if (document.getElementsByTagName("ErrCode") != null) {
                if (document.getElementsByTagName("ErrCode").item(0) != null) {
                    errorCode = document.getElementsByTagName("ErrCode").item(0).getTextContent();
                }
            }

            TerminalLog terminalLog = new TerminalLog();
            terminalLog.setTerminalID(getDeviceID());
            terminalLog.setMessage("Ошибка терминального приложения CaosPM, код ошибки: " + errorCode);
            terminalLog.setMessageSource(OperationType.RemoteMessage.APPLICATION_ERROR + " message");
            terminalLog.setTerminalDate(getDateTime());
            terminalLog.setServerDate(new Date());
            getLogService().saveLog(terminalLog);

            // TODO - открыть интервал простоя ПО, если необходимо

            log.info("End ApplicationError message processing for terminal: " + getDeviceID());
        } catch (Exception ex) {
            printStackTrace(ex, log);
        }
    }
}
