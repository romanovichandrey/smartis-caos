package by.bytechs.repository.dao.external;

import by.bytechs.repository.config.externalConfigTest.TestExternalRepositoryConfig;
import by.bytechs.repository.entity.external.SecurityGroup;
import by.bytechs.repository.entity.external.SecurityRole;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.TreeSet;

/**
 * @author Romanovich Andrei
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestExternalRepositoryConfig.class})
@Transactional
public class SecurityGroupDaoTest {
    @Autowired
    private SecurityGroupDao securityGroupDao;
    @Autowired
    private SecurityRoleDao securityRoleDao;

    @Before
    public void setUp() {
        SecurityRole role = new SecurityRole("1");
        securityRoleDao.save(role);
        SecurityGroup group = new SecurityGroup();
        group.setGroupName("monitoringAdminTest");
        group.setSecurityRoleSet(new TreeSet<>());
        group.getSecurityRoleSet().add(role);
        securityGroupDao.save(group);
    }

    @Test
    public void findByGroupNameTest() {
        SecurityGroup group = securityGroupDao.findByGroupName("monitoringAdminTest");
        Assert.assertNotNull(group);
        Assert.assertNotNull(group.getId());
        Assert.assertEquals(group.getSecurityRoleSet().size(), 1);
        group.getSecurityRoleSet().forEach(securityRole -> {
            Assert.assertEquals(securityRole.getRoleName(), "1");
        });
    }
}