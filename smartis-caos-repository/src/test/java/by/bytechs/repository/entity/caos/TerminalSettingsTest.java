package by.bytechs.repository.entity.caos;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by Kotsuba_VV on 19.01.2017.
 */

public class TerminalSettingsTest {
    @Test
    public void testFields() {
        TerminalSettings settings = new TerminalSettings();
        Date date = new Date();
        // check fields type
        settings.setCity("Минск", date);
        settings.setAddress("ул. Воронянского, 17", date);
        settings.setLatitude(53.878149, date);
        settings.setLongitude(27.548362, date);
        settings.setModel("CDS 707C", date);
        settings.setCaosWWWVersion("5.8.0", date);
        settings.setCaosPMVersion("5.8.0", date);
    }

    @Test
    public void testDates() throws IllegalAccessException {
        Date dateToCompare = new Date();
        TerminalSettings terminalSettings = new TerminalSettings();
        for (Field field : terminalSettings.getClass().getDeclaredFields()) {
            if (field.getName().contains("Md")) {
                field.setAccessible(true);
                Date date = (Date) field.get(terminalSettings);
                Assert.assertNotNull(field.getName() + " is NULL", date);
                //Assert.assertTrue(date.after(dateToCompare));
            }
        }
    }
}
