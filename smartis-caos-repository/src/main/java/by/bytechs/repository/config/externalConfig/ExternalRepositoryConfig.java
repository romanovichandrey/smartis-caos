package by.bytechs.repository.config.externalConfig;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@ComponentScan(basePackages = "by.bytechs.repository.config.externalConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"by.bytechs.repository.dao.external"},
        entityManagerFactoryRef = "externalEntityManager",
        transactionManagerRef = "externalTxManager")
public class ExternalRepositoryConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource externalDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("external.jdbc.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("external.jdbc.url"));
        dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("external.c3p0.initialSize")));
        dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("external.c3p0.minSize")));
        dataSource.setMaxStatements(Integer.parseInt(env.getProperty("external.c3p0.maxStatements")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("external.c3p0.maxSize")));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean externalEntityManager() throws PropertyVetoException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(externalDataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("external.hibernate.hbm2ddl.auto", env.getProperty("external.hibernate.hbm2ddl.auto"));
        jpaProperties.setProperty("external.hibernate.dialect", env.getProperty("external.hibernate.dialect"));
        jpaProperties.setProperty("external.hibernate.show_sql",env.getProperty("external.hibernate.show_sql"));
        factory.setJpaProperties(jpaProperties);
        factory.setPackagesToScan("by.bytechs.repository.entity.external");

        return factory;
    }

    @Bean
    public PlatformTransactionManager externalTxManager() throws PropertyVetoException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(externalEntityManager().getObject());
        return txManager;
    }
}