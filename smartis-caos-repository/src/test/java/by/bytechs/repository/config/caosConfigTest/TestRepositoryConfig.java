package by.bytechs.repository.config.caosConfigTest;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by Kotsuba_VV on 19.01.2017.
 */
@Configuration
@PropertySource("classpath:repositoryTest.properties")
@ComponentScan(basePackages = "by.bytechs.repository.config.caosConfigTest")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"by.bytechs.repository.dao.caos"},
        entityManagerFactoryRef = "testCaosEntityManagerFactory",
        transactionManagerRef = "testCaosTransactionManager")
public class TestRepositoryConfig {
    @Autowired
    private Environment environment;

    @Primary
    @Bean
    public DataSource testCaosDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(environment.getProperty("test.jdbc.driverClassName"));
        dataSource.setJdbcUrl(environment.getProperty("test.jdbc.url"));
        dataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("test.c3p0.initialSize")));
        dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("test.c3p0.minSize")));
        dataSource.setMaxStatements(Integer.parseInt(environment.getProperty("test.c3p0.maxStatements")));
        dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("test.c3p0.maxSize")));
        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean testCaosEntityManagerFactory() throws PropertyVetoException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(testCaosDataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("test.hibernate.hbm2ddl.auto", environment.getProperty("test.hibernate.hbm2ddl.auto"));
        jpaProperties.setProperty("test.hibernate.dialect", environment.getProperty("test.hibernate.dialect"));
        jpaProperties.setProperty("test.hibernate.show_sql", environment.getProperty("test.hibernate.show_sql"));
        factory.setJpaProperties(jpaProperties);
        factory.setPackagesToScan("by.bytechs.repository.entity.caos");

        return factory;
    }

    @Primary
    @Bean
    public PlatformTransactionManager testCaosTransactionManager() throws PropertyVetoException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(testCaosEntityManagerFactory().getObject());
        return txManager;
    }
}
