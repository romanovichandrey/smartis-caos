package by.bytechs.util.constraints;

/**
 * Created by Kotsuba_VV on 18.01.2017.
 */

public class DeviceStatusConstraints {
    public class DeviceShortName {
        public static final String BANKNOTE_MODULE = "BDM";
        public static final String IBUTTONREADER = "IBM";
        public static final String PRINTER_CF = "PRN"; // printer device name from caos function
        public static final String PRINTER_RM = "PTR"; // printer device name from remote message
    }

    public class GlobalDeviceStatus {
        public static final int OK = 0;
        public static final int KO = 1;
    }

    public class SensorCleaning {
        public static final int NOT_NECESSARY = 0;
        public static final int NECESSARY = 1;
    }

    public class DoorState {
        public static final int CLOSED = 0;
        public static final int IS_OPEN = 1;
    }

    public class DeviceState {
        public static final int BUSY = 0;
        public static final int ONLINE = 1;
        public static final int OFFLINE = 2;
        public static final int HARDWARE_ERROR = 3;
        public static final int FULL = 4;
    }

    public class BagStatus {
        public static final int EMPTY = 0;
        public static final int FULL = 1;
        public static final int OK = 2;
        public static final int CLOSED = 3;
        public static final int BLOCKED = 4;
        public static final int WRONG_POSITION = 5;
        public static final int NOT_PRESENT = 6;
        public static final int MISCOUNT = 7;
        public static final int MISCOUNT_OVERRIDDEN = 8;
        public static final int FULL_FILL_SENSOR_COVERED_BUT_EMPTY_BAG = 9;
        public static final int FULL_AND_MISCOUNT_OVERRIDDEN = 10;
    }

    public class BundlerStatus {
        public static final int UNKNOWN = -2;
        public static final int OK = 0;
        public static final int BLOCKED = 1;
        public static final int PAPER_NEAR_END = 2;
        public static final int PAPER_FINISHED = 3;
    }

    public class CoverStatus {
        public static final int CLOSED = 0;
        public static final int IS_OPEN = 1;
    }

    public class CashType {
        public static final int BANKNOTE = 0;
        public static final int COIN = 1;
    }

    public class ExtCode {
        public static final int DEVICE_BUSY = 10010;
        public static final int NO_NOTE_DEPOSITED = 10011;

    }
}
