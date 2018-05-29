package by.bytechs.repository.config.externalConfigTest;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
 * @author Romanovich Andrei
 */
@Configuration
@PropertySource("classpath:repositoryTest.properties")
@ComponentScan(basePackages = "by.bytechs.repository.config.externalConfigTest")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"by.bytechs.repository.dao.external"},
        entityManagerFactoryRef = "testExternalEntityManagerFactory",
        transactionManagerRef = "testExternalTransactionManager")
public class TestExternalRepositoryConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource testExternalDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(environment.getProperty("test.jdbc.driverClassName"));
        dataSource.setJdbcUrl(environment.getProperty("test.external.jdbc.url"));
        dataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("test.c3p0.initialSize")));
        dataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("test.c3p0.minSize")));
        dataSource.setMaxStatements(Integer.parseInt(environment.getProperty("test.c3p0.maxStatements")));
        dataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("test.c3p0.maxSize")));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean testExternalEntityManagerFactory() throws PropertyVetoException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(testExternalDataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("test.external.hibernate.hbm2ddl.auto", environment.getProperty("test.hibernate.hbm2ddl.auto"));
        jpaProperties.setProperty("test.external.hibernate.dialect", environment.getProperty("test.hibernate.dialect"));
        jpaProperties.setProperty("test.external.hibernate.show_sql", environment.getProperty("test.hibernate.show_sql"));
        factory.setJpaProperties(jpaProperties);
        factory.setPackagesToScan("by.bytechs.repository.entity.external");

        return factory;
    }

    @Bean
    public PlatformTransactionManager testExternalTransactionManager() throws PropertyVetoException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(testExternalEntityManagerFactory().getObject());
        return txManager;
    }
}