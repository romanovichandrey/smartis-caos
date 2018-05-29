package by.bytechs.web.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private NegotiateSecurityFilter negotiateSecurityFilter;
    @Autowired
    private NegotiateSecurityFilterEntryPoint entryPoint;
    @Autowired
    @Qualifier(value = "securityDataSource")
    private DataSource securityDataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("terminalLog/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                    .addFilterBefore(negotiateSecurityFilter, BasicAuthenticationFilter.class);
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource)
        .authoritiesByUsernameQuery("SELECT FROM SecurityGroup as group INNER JOIN GroupToRoleSecurity");
    }

}
