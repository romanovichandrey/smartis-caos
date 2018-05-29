package by.bytechs.repository.entity.caos;

import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.IButtonReader;
import by.bytechs.repository.entity.caos.Printer;
import by.bytechs.repository.entity.caos.Terminal;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 19.01.2017.
 */

public class TerminalTest {
    @Test
    public void testFields() {
        Terminal terminal = new Terminal();
        Date date = new Date();
        // check field types
        terminal.setTerminalID("CDS02456", date);
        terminal.setLogicalName("ДМ 02456", date);
        terminal.setHost("172.31.251.146", date);
        terminal.setPort(7004, date);

        // check inner objects init
        Assert.assertNotNull("Devices is null", terminal.getDevices());
        Assert.assertTrue("Device list is not empty", terminal.getDevices().isEmpty());
        Assert.assertNotNull("Settings is null", terminal.getTerminalSettings());
    }

    @Test
    public void testDevices() {
        Terminal terminal = new Terminal();

        // device list has to be initialized already
        terminal.getDevices().add(new BanknoteModule());
        terminal.getDevices().add(new IButtonReader());
        terminal.getDevices().add(new Printer());

        // check inheritance
        Assert.assertTrue(terminal.getDevices().get(0) instanceof BanknoteModule);

        BanknoteModule module = (BanknoteModule) terminal.getDevices().get(0);
        Assert.assertNotNull("BDM is null", module);
        Assert.assertNotNull("Cash is null", module.getCurrentCashUnits());

        Assert.assertTrue(terminal.getDevices().get(1) instanceof IButtonReader);
        Assert.assertTrue(terminal.getDevices().get(2) instanceof Printer);
    }

    @Test
    public void testDates() throws IllegalAccessException {
        Date dateToCompare = new Date();
        Terminal terminal = new Terminal();
        for (Field field : terminal.getClass().getDeclaredFields()) {
            if (field.getName().contains("Md")) {
                field.setAccessible(true);
                Date date = (Date) field.get(terminal);
                Assert.assertNotNull(field.getName() + " is NULL", date);
                //Assert.assertTrue(date.after(dateToCompare));
            }
        }
    }
}
