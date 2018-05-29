package by.bytechs.service.message.factory;

import by.bytechs.service.message.factory.handler.MessageHandler;
import by.bytechs.util.constraints.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 */

@Service
public class MessageProcessFactory {

    @Autowired
    @Qualifier("DeviceOK")
    private MessageHandler devOkHandler;
    @Autowired
    @Qualifier("DevInfo")
    private MessageHandler devInfoHandler;
    @Autowired
    @Qualifier("ComponentFail")
    private MessageHandler componentFailHandler;
    @Autowired
    @Qualifier("Deposit")
    private MessageHandler depositHandler;
    @Autowired
    @Qualifier("DoorOpen")
    private MessageHandler doorOpenHandler;
    @Autowired
    @Qualifier("CashBagRemoved")
    private MessageHandler cashBagRemovedHandler;
    @Autowired
    @Qualifier("CashRemoval")
    private MessageHandler cashRemovalHandler;
    @Autowired
    @Qualifier("CashBagPlaced")
    private MessageHandler cashBagPlacedHandler;
    @Autowired
    @Qualifier("DoorClosed")
    private MessageHandler doorClosedHandler;
    @Autowired
    @Qualifier("Shutdown")
    private MessageHandler shutdownHandler;
    @Autowired
    @Qualifier("Reboot")
    private MessageHandler rebootHandler;
    @Autowired
    @Qualifier("CashBagNearlyFull")
    private MessageHandler cashBagNearlyFullHandler;
    @Autowired
    @Qualifier("TransportJam")
    private MessageHandler transportJamHandler;
    @Autowired
    @Qualifier("ApplicationError")
    private MessageHandler applicationErrorHandler;

    public MessageHandler getHandler(String operationType) {

        if (operationType == null) {
            return null;
        }

        switch (operationType) {
            case OperationType.RemoteMessage.DEVICE_OK: return devOkHandler;
            case OperationType.RemoteMessage.DEV_INFO:  return devInfoHandler;
            case OperationType.RemoteMessage.COMPONENT_FAIL: return componentFailHandler;
            case OperationType.RemoteMessage.DEPOSIT: return depositHandler;
            case OperationType.RemoteMessage.DOOR_OPEN: return doorOpenHandler;
            case OperationType.RemoteMessage.CASH_BAG_REMOVED: return cashBagRemovedHandler;
            case OperationType.RemoteMessage.CASH_REMOVAL: return cashRemovalHandler;
            case OperationType.RemoteMessage.CASH_BAG_PLACED: return cashBagPlacedHandler;
            case OperationType.RemoteMessage.DOOR_CLOSED: return doorClosedHandler;
            case OperationType.RemoteMessage.SHUTDOWN: return shutdownHandler;
            case OperationType.RemoteMessage.REBOOT: return rebootHandler;
            case OperationType.RemoteMessage.CASH_BAG_NEARLY_FULL: return cashBagNearlyFullHandler;
            case OperationType.RemoteMessage.TRANSPORT_JAM: return transportJamHandler;
            case OperationType.RemoteMessage.APPLICATION_ERROR: return applicationErrorHandler;
        }

        return null;
    }
}
