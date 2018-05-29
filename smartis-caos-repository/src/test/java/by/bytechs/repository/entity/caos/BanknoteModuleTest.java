package by.bytechs.repository.entity.caos;

import by.bytechs.repository.entity.caos.BanknoteModule;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */
public class BanknoteModuleTest {
    @Test
    public void testFields() {
        BanknoteModule module = new BanknoteModule();
        Date date = new Date();
        // check field types
        module.setStatus(0, date);
        module.setState(0, date);
        module.setBagStatus(2, date);
        module.setCleaning(0, date);
        module.setDoor(0, date);
        module.setBundlerStatus(0, date);
        module.setCover(0, date);
        Assert.assertNotNull("Cash is null", module.getCurrentCashUnits());
        Assert.assertTrue("Cash is not empty", module.getCurrentCashUnits().isEmpty());
    }

    @Test
    public void testDates() throws IllegalAccessException {
        Date dateToCompare = new Date();
        BanknoteModule banknoteModule = new BanknoteModule();
        for (Field field : banknoteModule.getClass().getDeclaredFields()) {
            if (field.getName().contains("Md")) {
                field.setAccessible(true);
                Date date = (Date) field.get(banknoteModule);
                Assert.assertNotNull(field.getName() + " is NULL", date);
                //Assert.assertTrue("", date.after(dateToCompare));
            }
        }
    }
}
