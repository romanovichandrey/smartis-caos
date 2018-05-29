package by.bytechs.util.dictionary;

import by.bytechs.util.constraints.DeviceStatusConstraints;

/**
 * Created by Kotsuba_VV on 14.02.2017.
 */
public class StatusDescriptionDictionary {
    public static String getGlobalStatusDescription(int status) {
        switch (status) {
            case DeviceStatusConstraints.GlobalDeviceStatus.OK: return "Устройство готово к работе";
            case DeviceStatusConstraints.GlobalDeviceStatus.KO: return "Устройство не работает";
        }
        return "";
    }

    public static String getDevStateDescription(int state) {
        switch (state) {
            case DeviceStatusConstraints.DeviceState.BUSY: return "";
            case DeviceStatusConstraints.DeviceState.ONLINE : return "Устройство на связи";
            case DeviceStatusConstraints.DeviceState.OFFLINE: return "Устройство не на связи";
            case DeviceStatusConstraints.DeviceState.HARDWARE_ERROR: return "Аппаратная ошибка";
            case DeviceStatusConstraints.DeviceState.FULL: return "Устройство заполнено";
        }
        return "";
    }

    public static String getCleaningStatusDescription(int status) {
        switch (status) {
            case DeviceStatusConstraints.SensorCleaning.NOT_NECESSARY: return "Чистка сенсоров не нужна";
            case DeviceStatusConstraints.SensorCleaning.NECESSARY: return "Необходима чистка сенсоров";
        }
        return "";
    }

    public static String getDoorStatusDescription(int status) {
        switch (status) {
            case DeviceStatusConstraints.DoorState.CLOSED: return "Дверь сейфа закрыта";
            case DeviceStatusConstraints.DoorState.IS_OPEN: return "Дверь сейфа открыта";
        }
        return "";
    }

    public static String getBagStatusDescription(int status) {
        switch (status) {
            case DeviceStatusConstraints.BagStatus.EMPTY: return "Мешок пуст";
            case DeviceStatusConstraints.BagStatus.FULL: return "Мешок полон";
            case DeviceStatusConstraints.BagStatus.OK: return "Мешок в порядке";
            case DeviceStatusConstraints.BagStatus.CLOSED: return "Мешок закрыт";
            case DeviceStatusConstraints.BagStatus.BLOCKED: return "Мешок блокирован";
            case DeviceStatusConstraints.BagStatus.WRONG_POSITION: return "Неверная позиция мешка";
            case DeviceStatusConstraints.BagStatus.NOT_PRESENT: return "Мешок отсутствует";
            case DeviceStatusConstraints.BagStatus.MISCOUNT: return "Неправильный подсчет в мешке";
            case DeviceStatusConstraints.BagStatus.MISCOUNT_OVERRIDDEN: return "Неправильный подсчет переопределен";
            case DeviceStatusConstraints.BagStatus.FULL_FILL_SENSOR_COVERED_BUT_EMPTY_BAG: return "Датчик наполнения закрыт, но мешок пуст";
            case DeviceStatusConstraints.BagStatus.FULL_AND_MISCOUNT_OVERRIDDEN: return "Мешок полон и неправильный подсчет переопределен";
        }
        return "";
    }

    public static String getBundlerStatusDescription(int status) {
        switch (status) {
            case DeviceStatusConstraints.BundlerStatus.UNKNOWN: return "Неизвестный статус устройства упаковщика";
            case DeviceStatusConstraints.BundlerStatus.OK: return "Упаковшик в порядке";
            case DeviceStatusConstraints.BundlerStatus.BLOCKED: return "Упаковщик блокирован";
            case DeviceStatusConstraints.BundlerStatus.PAPER_NEAR_END: return "Бумага подходит к концу";
            case DeviceStatusConstraints.BundlerStatus.PAPER_FINISHED: return "Бумага закончилась";
        }
        return "";
    }

    public static String getCoverStatusDescription(int status) {
        switch (status) {
            case DeviceStatusConstraints.CoverStatus.CLOSED: return "Крышка закрыта";
            case DeviceStatusConstraints.CoverStatus.IS_OPEN: return "Крышка открыта";
        }
        return "";
    }



}
