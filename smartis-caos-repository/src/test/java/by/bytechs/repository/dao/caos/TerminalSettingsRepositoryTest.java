package by.bytechs.repository.dao.caos;

import by.bytechs.repository.config.caosConfigTest.TestRepositoryConfig;
import by.bytechs.repository.dao.caos.TerminalSettingsRepository;
import by.bytechs.repository.entity.caos.TerminalSettings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kotsuba_VV on 20.01.2017.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestRepositoryConfig.class})
@Transactional
public class TerminalSettingsRepositoryTest {
    @Autowired
    private TerminalSettingsRepository terminalSettingsRepository;

    @Test
    public void testSave() {
        TerminalSettings settings = new TerminalSettings();
        settings = terminalSettingsRepository.save(settings);
        Assert.assertTrue("Settings are not found!", terminalSettingsRepository.exists(settings.getId()));
    }
}
