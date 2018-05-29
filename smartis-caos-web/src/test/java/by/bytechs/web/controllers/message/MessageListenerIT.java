package by.bytechs.web.controllers.message;

import by.bytechs.dto.TerminalDTO;
import by.bytechs.repository.entity.caos.Terminal;
import by.bytechs.service.CashUnitHistoryService;
import by.bytechs.service.TerminalService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 26.01.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MessageListenerIT {
    @Autowired
    Environment env;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private CashUnitHistoryService cashUnitHistoryService;

    private Socket socket;
    private PrintWriter out;

    private int terminalID;
    private Date messageDate;

    public static final String DEVICE_OK_COMMAND = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<RemoteMessage operation=\"Device OK\" DeviceID=\"CDS02457\" CustomerCode=\"\" NOP=\"11288\" " +
            "Date=\"2017-01-18\" Time=\"11:07:19\"> <content><count den=\"50\" curr=\"BYR\" qty=\"19\" type=\"B\" " +
            "N=\"0\" sType=\"B\"/><count den=\"100\" curr=\"BYR\" qty=\"1\" type=\"B\" N=\"0\" sType=\"B\"/></content>" +
            "<devStatus><dev name=\"BDM\" mod=\"CDS 707C\" err=\"OK\" clean=\"0\" door=\"0\" devS=\"1\" bag=\"2\" cov=\"0\" " +
            "blk=\"00000000\" bund=\"-2\" ext=\"0 \"/><dev name=\"IBM\" err=\"OK\" devS=\"1\" ext=\"0 \"/><dev name=\"PTR\" " +
            "err=\"OK\" devS=\"1\" ext=\"0000 \"/></devStatus></RemoteMessage>";

    public void prepareSocket() throws IOException, InterruptedException {
        // prepare date from message
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 18);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 7);
        calendar.set(Calendar.SECOND, 19);
        messageDate = calendar.getTime();

        // send deviceOk message
        socket = new Socket();
        socket.connect(new InetSocketAddress(env.getProperty("socket.host"), Integer.parseInt(env.getProperty("socket.port"))), 5000);
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Test
    public void testDeviceOkMessage() throws InterruptedException, IOException {
        prepareTerminal();
        prepareSocket();

        boolean exists = terminalService.isTerminalExists("CDS02457");
        Assert.assertTrue("terminal CDS02457 is not exist", exists);
        Assert.assertTrue("Socket is not connected", socket.isConnected());

        out.write(DEVICE_OK_COMMAND);
        out.flush();


        Terminal terminal = terminalService.findByTerminalID("CDS02457");
        Assert.assertTrue("TerminalID is not equal", terminal.getId() == terminalID);
        Assert.assertNotNull("terminal settings is null", terminal.getTerminalSettings());
        /*Assert.assertNotNull("model is null", terminal.getTerminalSettings().getModel());
        Assert.assertTrue(terminal.getTerminalSettings().getModel().equals("CDS 707C"));
        CashUnitHistoryHeader header = cashUnitHistoryService.findLastByTerminal(terminal);
        Assert.assertTrue("dates are not equal", messageDate.equals(header.getMessageDate()));
        Assert.assertTrue("initiators are not equal", header.getInitiator().equals("Incoming DeviceOK Message"));
        List<CashUnitHistory> cashUnits = header.getCashUnits();
        Assert.assertTrue("cash units size <> 2", cashUnits.size() == 2);
        CashUnitHistory cashUnitHistory50;
        CashUnitHistory cashUnitHistory100;
        if (cashUnits.get(0).getDenomination() == 50) {
            cashUnitHistory50 = cashUnits.get(0);
            cashUnitHistory100 = cashUnits.get(1);
        } else {
            cashUnitHistory50 = cashUnits.get(1);
            cashUnitHistory100 = cashUnits.get(0);
        }

        Assert.assertTrue("currency is not BYR", cashUnitHistory50.getCurrency().equals("BYR"));
        Assert.assertTrue("denominations are not equal", cashUnitHistory50.getDenomination() == 50);
        Assert.assertTrue("quantities are not equal", cashUnitHistory50.getQuantity() == 19);
        Assert.assertTrue("cash types are not equal", cashUnitHistory50.getType() == DeviceStatusConstraints.CashType.BANKNOTE);

        Assert.assertTrue("currency is not BYR", cashUnitHistory100.getCurrency().equals("BYR"));
        Assert.assertTrue("denominations are not equal", cashUnitHistory100.getDenomination() == 100);
        Assert.assertTrue("quantities are not equal", cashUnitHistory100.getQuantity() == 1);
        Assert.assertTrue("cash types are not equal", cashUnitHistory100.getType() == DeviceStatusConstraints.CashType.BANKNOTE);*/
    }

    private void prepareTerminal() throws InterruptedException {
        // prepare terminal for incoming message
        TerminalDTO terminalDTO = new TerminalDTO();
        terminalDTO.setTerminalID("CDS02457");
        terminalDTO.setLogicalName("ДМ 2457");
        terminalDTO.setHost("172.31.251.146");
        terminalDTO.setPort(704);
        terminalID = terminalService.saveTerminalDTO(terminalDTO);
        Thread.sleep(10000);
    }

    @After
    public void closeSocket() throws IOException {
        out.close();
        socket.close();
    }
}
