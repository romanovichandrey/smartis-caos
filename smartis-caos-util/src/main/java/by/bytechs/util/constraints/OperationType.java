package by.bytechs.util.constraints;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 */
public class OperationType {
    public class RemoteMessage {
        public static final String DEVICE_OK = "Device OK";
        public static final String DEV_INFO = "DevInfo";
        public static final String COMPONENT_FAIL = "Component fail";
        public static final String DEPOSIT = "Deposit";
        public static final String DOOR_OPEN = "Vault door open";
        public static final String CASH_BAG_REMOVED = "Cash bag removed";
        public static final String CASH_REMOVAL = "Cash removal";
        public static final String CASH_BAG_PLACED = "Cash bag placed";
        public static final String DOOR_CLOSED = "Vault door closed";
        public static final String SHUTDOWN = "Machine shutdown";
        public static final String REBOOT = "Machine reboot";
        public static final String CASH_BAG_NEARLY_FULL = "Cash bag nearly full";
        public static final String TRANSPORT_JAM = "Transport Jam";
        public static final String APPLICATION_ERROR = "Application Error";
    }

    public class TransactionType {
        public static final int DEPOSIT = 0;
        public static final int WITHDRAWAL = 1;
    }
}
