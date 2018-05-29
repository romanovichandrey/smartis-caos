package by.bytechs.repository.dao.caos;

import by.bytechs.repository.config.caosConfigTest.TestRepositoryConfig;
import by.bytechs.repository.dao.caos.TerminalRepository;
import by.bytechs.repository.entity.caos.Terminal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 19.01.2017.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class TerminalRepositoryTest {
    @Autowired
    private TerminalRepository terminalRepository;

    @Test
    public void saveTerminal() {
        Terminal terminal = new Terminal();
        Date date = new Date();

        terminal.setTerminalID("CDS02456", date);
        terminal.setLogicalName("ДМ 02456", date);
        terminal.setHost("172.31.251.147", date);
        terminal.setPort(704, date);

        terminal.getTerminalSettings().setCity("Минск", date);
        terminal.getTerminalSettings().setAddress("ул. Воронянского, 17", date);
        terminal.getTerminalSettings().setLatitude(53.878149, date);
        terminal.getTerminalSettings().setLongitude(27.548362, date);
        terminal.getTerminalSettings().setModel("CDS 707C", date);
        terminal.getTerminalSettings().setCaosWWWVersion("5.8.0", date);
        terminal.getTerminalSettings().setCaosPMVersion("5.8.0", date);
        terminal = terminalRepository.save(terminal);

        Assert.assertTrue("Terminal not exists!", terminalRepository.exists(terminal.getId()));
        Assert.assertNotNull("TerminalSettings is Null!", terminalRepository.findByTerminalID(terminal.getTerminalID()).getTerminalSettings());
    }
}
