package by.bytechs.repository.config.caosConfig;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 * Created by Kotsuba_VV on 11.01.2017.
 */

@Configuration
@ComponentScan(basePackages = "by.bytechs.repository.config.caosConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"by.bytechs.repository.dao.caos"},
        entityManagerFactoryRef = "caosEntityManager",
        transactionManagerRef = "caosTxManager")
public class RepositoryConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    public DataSource caosDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.driverClassName"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("c3p0.initialSize")));
        dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("c3p0.minSize")));
        dataSource.setMaxStatements(Integer.parseInt(env.getProperty("c3p0.maxStatements")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("c3p0.maxSize")));
        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean caosEntityManager() throws PropertyVetoException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(caosDataSource());
        factory.setJpaVendorAdapter(vendorAdapter);
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        jpaProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        jpaProperties.setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
        factory.setJpaProperties(jpaProperties);
        factory.setPackagesToScan("by.bytechs.repository.entity.caos");

        return factory;
    }

    @Primary
    @Bean
    public PlatformTransactionManager caosTxManager() throws PropertyVetoException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(caosEntityManager().getObject());
        return txManager;
    }
}
