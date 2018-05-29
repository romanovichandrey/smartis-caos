package by.bytechs.ui.config;

import by.bytechs.ui.restService.utils.MyResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * @author Romanovich Andrei
 */
@Configuration
public class MainAppConfig {

    @Autowired
    private Environment environment;
    @Autowired
    private MyResponseErrorHandler myResponseErrorHandler;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(myResponseErrorHandler);
        return restTemplate;
    }

    @Bean
    public String getAllTerminals() {
        return environment.getProperty("caos.url") + "terminals/getAllTerminals/";
    }

    @Bean
    public String getAllTerminalsInfo() {
        return environment.getProperty("caos.url") + "terminals/getAllTerminalsInfo/";
    }

    @Bean
    public String getAllUsers() {
        return environment.getProperty("caos.url") + "users/getAllUsers/";
    }

    @Bean
    public String getAllOrganization() {
        return environment.getProperty("caos.url") + "users/getAllOrganization/";
    }

    @Bean
    public String getAllUserType() {
        return environment.getProperty("caos.url") + "users/getAllUserType/";
    }

    @Bean
    public String addNewAccount() {
        return environment.getProperty("caos.url") + "users/addNewAccount/{organizationId}";
    }

    @Bean
    public String addOrUpdateOrganization() {
        return environment.getProperty("caos.url") + "users/addOrUpdateOrganization/";
    }

    @Bean
    public String deleteAccount() {
        return environment.getProperty("caos.url") + "users/deleteAccount/{id}";
    }

    @Bean
    public String addNewUser() {
        return environment.getProperty("caos.url") + "users/addNewUser/";
    }

    @Bean
    public String deleteUser() {
        return environment.getProperty("caos.url") + "users/deleteUser/{id}";
    }

}
