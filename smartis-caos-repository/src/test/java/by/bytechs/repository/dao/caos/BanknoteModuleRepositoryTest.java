package by.bytechs.repository.dao.caos;

import by.bytechs.repository.config.caosConfigTest.TestRepositoryConfig;
import by.bytechs.repository.dao.caos.BDMRepository;
import by.bytechs.repository.dao.caos.CashUnitRepository;
import by.bytechs.repository.dao.caos.TerminalRepository;
import by.bytechs.repository.entity.caos.BanknoteModule;
import by.bytechs.repository.entity.caos.CashUnit;
import by.bytechs.repository.entity.caos.Terminal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@Transactional
public class BanknoteModuleRepositoryTest {
    @Autowired
    private BDMRepository bdmRepository;
    @Autowired
    private CashUnitRepository cashUnitRepository;
    @Autowired
    private TerminalRepository terminalRepository;

    private Date date;

    @Before
    public void initDate() {
        date = new Date();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSaveWithoutTerminal() {
        BanknoteModule module = new BanknoteModule();
        bdmRepository.save(module);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCashUnitSaveWithoutBanknoteModule() {
        CashUnit cashUnit = new CashUnit();
        cashUnit.setCurrency("BYN");
        cashUnit.setDenomination(10);
        cashUnit.setQuantity(17);
        cashUnit.setType(1);
        cashUnitRepository.save(cashUnit);
    }

    @Test
    public void testCorrectSave() {
        BanknoteModule module = new BanknoteModule();

        Terminal terminal = new Terminal();
        terminal.setTerminalID("Test0000", date);
        terminal.setLogicalName("Logic000", date);
        terminal.setHost("192.168.1.1", date);
        terminal.setPort(55555, date);
        terminal.getDevices().add(module);
        module.setTerminal(terminal);
        terminalRepository.save(terminal);

        CashUnit cashUnit = new CashUnit();
        cashUnit.setCurrency("BYN");
        cashUnit.setDenomination(10);
        cashUnit.setQuantity(17);
        cashUnit.setType(1);
        cashUnit.setModifyDate(date);
        cashUnit.setBanknoteModule(module);
        module.getCurrentCashUnits().add(cashUnit);
        module = bdmRepository.save(module);

        Assert.assertTrue("Module has more than one cash unit!", module.getCurrentCashUnits().size() == 1);

        cashUnit = module.getCurrentCashUnits().get(0);
        Assert.assertTrue("Cash unit was not saved", cashUnitRepository.exists(cashUnit.getId()));

        terminal = module.getTerminal();
        Assert.assertTrue("Terminal was not saved", terminalRepository.exists(terminal.getId()));
    }


}
