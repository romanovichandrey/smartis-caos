package by.bytechs.service.message.factory.handler;

import by.bytechs.repository.entity.caos.*;
import by.bytechs.service.TerminalLogService;
import by.bytechs.util.constraints.DeviceStatusConstraints;
import by.bytechs.util.dictionary.StatusDescriptionDictionary;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Kotsuba_VV on 25.01.2017.
 */

public abstract class MessageHandler {
    private String deviceID;
    private Date dateTime;
    private String messageType;

    @Autowired
    private TerminalLogService terminalLogService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public abstract void processMessage(Document document);

    protected void init(Document document, String messageType) {
        NodeList nodeList = document.getElementsByTagName("RemoteMessage");
        String terminalID = ((Element) nodeList.item(0)).getAttribute("DeviceID");

        String dateString = ((Element) nodeList.item(0)).getAttribute("Date");
        String timeString = ((Element) nodeList.item(0)).getAttribute("Time");
        Date date = null;
        Date time = null;
        Date dateTime = new Date();
        try {
            date = dateFormat.parse(dateString);
            time = timeFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null && time != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(time);
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
            dateTime = calendar.getTime();
        }
        setDeviceID(terminalID);
        setDateTime(dateTime);
        setMessageType(messageType);
    }

    protected Terminal updateDevices(NodeList deviceList, Terminal terminal) {
        try {
            if (deviceList != null && deviceList.getLength() > 0) {
                for (int i = 0; i < deviceList.getLength(); i++) {
                    Node deviceNode = deviceList.item(i);
                    String device = deviceNode.getAttributes().getNamedItem("name").getNodeValue();
                    int deviceStatus = deviceNode.getAttributes().getNamedItem("err").getNodeValue().equals("OK") ?
                            DeviceStatusConstraints.GlobalDeviceStatus.OK : DeviceStatusConstraints.GlobalDeviceStatus.KO;
                    int deviceState = Integer.parseInt(deviceNode.getAttributes().getNamedItem("devS").getNodeValue().trim());
                    String extCode = deviceNode.getAttributes().getNamedItem("ext").getNodeValue().trim();
                    switch (device) {
                        case DeviceStatusConstraints.DeviceShortName.BANKNOTE_MODULE:
                            String model = deviceNode.getAttributes().getNamedItem("mod").getNodeValue();
                            int cleaningStatus = Integer.parseInt(deviceNode.getAttributes().getNamedItem("clean").getNodeValue().trim());
                            int doorStatus = Integer.parseInt(deviceNode.getAttributes().getNamedItem("door").getNodeValue().trim());
                            int bagStatus = Integer.parseInt(deviceNode.getAttributes().getNamedItem("bag").getNodeValue().trim());
                            int coverStatus = Integer.parseInt(deviceNode.getAttributes().getNamedItem("cov").getNodeValue().trim());
                            String bulkerStatus = deviceNode.getAttributes().getNamedItem("blk").getNodeValue();
                            int bundlerStatus = Integer.parseInt(deviceNode.getAttributes().getNamedItem("bund").getNodeValue().trim());
                            updateBanknoteModule(terminal, deviceStatus, model, cleaningStatus, doorStatus, deviceState, bagStatus,
                                    coverStatus, bundlerStatus, extCode, getDateTime());
                            break;
                        case DeviceStatusConstraints.DeviceShortName.IBUTTONREADER:
                            updateIButtonReader(terminal, deviceStatus, deviceState, extCode, getDateTime());
                            break;
                        case DeviceStatusConstraints.DeviceShortName.PRINTER_RM:
                            updatePrinter(terminal, deviceStatus, deviceState, extCode, getDateTime());
                            break;
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        return terminal;
    }

    private Terminal updateBanknoteModule(Terminal terminal, int globalStatus, String model, int clean, int door, int devState, int bag,
                                          int cover, int bundler, String extCode, Date modifyDate) {
        // check for updates vars
        boolean isGlobalStatusChanged;
        boolean isDevStatusChanged;
        boolean isExtCodeChanged;
        boolean isCleaningChanged;
        boolean isDoorChanged;
        boolean isBagStatusChanged;
        boolean isBundlerStatusChanged;
        boolean isCoverChanged;

        TerminalSettings terminalSettings = terminal.getTerminalSettings();
        if (terminalSettings == null) {
            terminalSettings = new TerminalSettings();
            terminal.setTerminalSettings(terminalSettings);
        }
        terminal.getTerminalSettings().setModel(model, modifyDate);
        List<Device> devices = terminal.getDevices();
        if (devices == null) {
            devices = new ArrayList<>();
        }
        BanknoteModule banknoteModule = null;
        for (Device device : devices) {
            if (device instanceof BanknoteModule) {
                banknoteModule = (BanknoteModule) device;
            }
        }
        if (banknoteModule == null) {
            banknoteModule = new BanknoteModule();
        } else {
            devices.remove(banknoteModule);
        }

        isGlobalStatusChanged  = banknoteModule.setStatus(globalStatus, modifyDate);
        isDevStatusChanged     = banknoteModule.setState(devState, modifyDate);
        isExtCodeChanged       = banknoteModule.setExtCode(extCode, modifyDate);
        isCleaningChanged      = banknoteModule.setCleaning(clean, modifyDate);
        isDoorChanged          = banknoteModule.setDoor(door, modifyDate);
        isBagStatusChanged     = banknoteModule.setBagStatus(bag, modifyDate);
        isBundlerStatusChanged = banknoteModule.setBundlerStatus(bundler, modifyDate);
        isCoverChanged         = banknoteModule.setCover(cover, modifyDate);
        banknoteModule.setTerminal(terminal);
        devices.add(banknoteModule);
        terminal.setDevices(devices);
        String description = DeviceStatusConstraints.DeviceShortName.BANKNOTE_MODULE + ": ";

        if (isGlobalStatusChanged) {
            // save changed status to log
            saveLog(description + StatusDescriptionDictionary.getGlobalStatusDescription(globalStatus));

            // TODO - open or close bdm interval
        }
        if (isDevStatusChanged) {
            saveLog(description + StatusDescriptionDictionary.getDevStateDescription(devState));
        }
        if (isCleaningChanged) {
            saveLog(description + StatusDescriptionDictionary.getCleaningStatusDescription(clean));
        }
        if (isDoorChanged) {
            saveLog(description + StatusDescriptionDictionary.getDoorStatusDescription(door));
        }
        if (isBagStatusChanged) {
            saveLog(description + StatusDescriptionDictionary.getBagStatusDescription(bag));
        }
        if (isBundlerStatusChanged) {
            saveLog(description + StatusDescriptionDictionary.getBundlerStatusDescription(bundler));
        }
        if (isCoverChanged) {
            saveLog(description + StatusDescriptionDictionary.getCoverStatusDescription(cover));
        }

        // TODO - decide what to do with ext code

        return terminal;
    }

    private Terminal updateIButtonReader(Terminal terminal, int globalStatus, int deviceStatus, String extCode, Date modifyDate) {
        // check for updates vars
        boolean isGlobalStatusChanged;
        boolean isDevStatusChanged;
        boolean isExtCodeChanged;

        IButtonReader reader = null;
        List<Device> devices = terminal.getDevices();
        if (devices == null) {
            devices = new ArrayList<>();
        }
        for (Device device : devices) {
            if (device instanceof IButtonReader) {
                reader = (IButtonReader) device;
            }
        }
        if (reader == null) {
            reader = new IButtonReader();
        } else {
            devices.remove(reader);
        }
        isGlobalStatusChanged = reader.setStatus(globalStatus, modifyDate);
        isDevStatusChanged    = reader.setState(deviceStatus, modifyDate);
        isExtCodeChanged      = reader.setExtCode(extCode, modifyDate);
        reader.setTerminal(terminal);
        devices.add(reader);
        terminal.setDevices(devices);

        String description = DeviceStatusConstraints.DeviceShortName.IBUTTONREADER + ": ";

        if (isGlobalStatusChanged) {
            // save changed status to log
            saveLog(description + StatusDescriptionDictionary.getGlobalStatusDescription(globalStatus));

            // TODO - open or close iButton interval
        }
        if (isDevStatusChanged) {
            saveLog(description + StatusDescriptionDictionary.getDevStateDescription(deviceStatus));
        }

        // TODO - decide what to do with ext code

        return terminal;
    }

    private Terminal updatePrinter(Terminal terminal, int globalStatus, int deviceStatus, String extCode, Date modifyDate) {
        // check for updates vars
        boolean isGlobalStatusChanged;
        boolean isDevStatusChanged;
        boolean isExtCodeChanged;

        Printer printer = null;
        List<Device> devices = terminal.getDevices();
        if (devices == null) {
            devices = new ArrayList<>();
        }
        for (Device device : devices) {
            if (device instanceof Printer) {
                printer = (Printer) device;
            }
        }
        if (printer == null) {
            printer = new Printer();
        } else {
            devices.remove(printer);
        }
        isGlobalStatusChanged = printer.setStatus(globalStatus, modifyDate);
        isDevStatusChanged    = printer.setState(deviceStatus, modifyDate);
        isExtCodeChanged      = printer.setExtCode(extCode, modifyDate);
        printer.setTerminal(terminal);
        devices.add(printer);
        terminal.setDevices(devices);

        String description = DeviceStatusConstraints.DeviceShortName.PRINTER_RM + ": ";

        if (isGlobalStatusChanged) {
            // save changed status to log
            saveLog(description + StatusDescriptionDictionary.getGlobalStatusDescription(globalStatus));

            // TODO - open or close printer interval
        }
        if (isDevStatusChanged) {
            saveLog(description + StatusDescriptionDictionary.getDevStateDescription(deviceStatus));
        }

        // TODO - decide what to do with ext code

        return terminal;
    }

    private void saveLog(String description) {
        TerminalLog log = new TerminalLog();
        log.setTerminalID(getDeviceID());
        log.setMessage(description);
        log.setMessageSource(getMessageType() + " message");
        log.setServerDate(new Date());
        log.setTerminalDate(getDateTime());
        terminalLogService.saveLog(log);
    }

    protected void printStackTrace(Exception ex, Logger log) {
        for (StackTraceElement element : ex.getStackTrace()) {
            log.error("\tat " + element.toString());
        }
        log.error(ex.toString());
    }

    protected CashUnitHistoryHeader getCashStructure(NodeList detailsNodeList, Terminal terminal) {
        List<CashUnitHistory> cashUnitHistoryList = new ArrayList<>();
        CashUnitHistoryHeader header = new CashUnitHistoryHeader();
        header.setTerminal(terminal);
        header.setMessageDate(getDateTime());
        header.setInitiator(messageType);

        if (detailsNodeList != null && detailsNodeList.getLength() > 0) {
            for (int i = 0; i < detailsNodeList.getLength(); i++) {
                Node detailsNode = detailsNodeList.item(i);
                String currency = detailsNode.getAttributes().getNamedItem("Currency").getNodeValue();

                NodeList countedNodeList = ((Element) detailsNode).getElementsByTagName("counted");

                if (countedNodeList != null && countedNodeList.getLength() > 0) {
                    for (int j = 0; j < countedNodeList.getLength(); j++) {
                        Node countedNode = countedNodeList.item(j);
                        int denomination = Integer.parseInt(countedNode.getAttributes().getNamedItem("denom").getNodeValue());
                        int quantity = Integer.parseInt(countedNode.getAttributes().getNamedItem("quantity").getNodeValue());
                        int cashType = countedNode.getAttributes().getNamedItem("type").getNodeValue().equals("B") ?
                                DeviceStatusConstraints.CashType.BANKNOTE : DeviceStatusConstraints.CashType.COIN;

                        CashUnitHistory cashUnitHistory = new CashUnitHistory();
                        cashUnitHistory.setCurrency(currency);
                        cashUnitHistory.setDenomination(denomination);
                        cashUnitHistory.setQuantity(quantity);
                        cashUnitHistory.setType(cashType);
                        cashUnitHistoryList.add(cashUnitHistory);
                    }
                }

            }
        }

        header.setCashUnits(cashUnitHistoryList);
        cashUnitHistoryList.stream().forEach(cashUnitHistory -> cashUnitHistory.setHeader(header));

        return header;
    }

    public String getDeviceID() { return deviceID; }
    public void setDeviceID(String deviceID) { this.deviceID = deviceID; }

    public Date getDateTime() { return dateTime; }
    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public TerminalLogService getLogService() { return terminalLogService; }
}
