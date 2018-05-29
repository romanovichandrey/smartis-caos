package by.bytechs.web.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import waffle.servlet.spi.BasicSecurityFilterProvider;
import waffle.servlet.spi.NegotiateSecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProviderCollection;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

import javax.servlet.Filter;

@Configuration
public class WaffleConfig {
    @Autowired
    private Environment environment;

    @Bean
    public WindowsAuthProviderImpl waffleWindowsAuthProvider() {
        return new WindowsAuthProviderImpl();
    }

    @Bean
    public NegotiateSecurityFilterProvider negotiateSecurityFilterProvider(
            WindowsAuthProviderImpl windowsAuthProvider) {
        return new NegotiateSecurityFilterProvider(windowsAuthProvider);
    }

    @Bean
    public BasicSecurityFilterProvider basicSecurityFilterProvider(WindowsAuthProviderImpl windowsAuthProvider) {
        return new BasicSecurityFilterProvider(windowsAuthProvider);
    }

    @Bean
    public SecurityFilterProviderCollection waffleSecurityFilterProviderCollection(
            NegotiateSecurityFilterProvider negotiateSecurityFilterProvider,
            BasicSecurityFilterProvider basicSecurityFilterProvider) {
        SecurityFilterProvider[] securityFilterProviders = {
                negotiateSecurityFilterProvider,
                basicSecurityFilterProvider };
        return new SecurityFilterProviderCollection(securityFilterProviders);
    }

    @Bean
    public NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint(
            SecurityFilterProviderCollection securityFilterProviderCollection) {
        NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint = new NegotiateSecurityFilterEntryPoint();
        negotiateSecurityFilterEntryPoint.setProvider(securityFilterProviderCollection);
        return negotiateSecurityFilterEntryPoint;
    }

    @Bean
    public NegotiateSecurityFilter waffleNegotiateSecurityFilter(SecurityFilterProviderCollection securityFilterProviderCollection) {
        NegotiateSecurityFilter negotiateSecurityFilter = new NegotiateSecurityFilter();
        negotiateSecurityFilter.setProvider(securityFilterProviderCollection);
        return negotiateSecurityFilter;
    }
    
    // This is required for Spring Boot so it does not register the same filter twice
    @Bean
    public FilterRegistrationBean waffleNegotiateSecurityFilterRegistration(NegotiateSecurityFilter waffleNegotiateSecurityFilter) {
    	FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    	registrationBean.setFilter((Filter) waffleNegotiateSecurityFilter);
    	registrationBean.setEnabled(false);
    	return registrationBean;
    }

    @Bean(name = "securityDataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("external.jdbc.driverClassName"));
        driverManagerDataSource.setUrl(environment.getProperty("external.jdbc.url"));
        return driverManagerDataSource;
    }
    
}
