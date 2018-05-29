package by.bytechs.service.config;

import by.bytechs.repository.config.caosConfig.RepositoryConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Kotsuba_VV on 11.01.2017.
 */

@Configuration
@ComponentScan(basePackages = "by.bytechs.service")
@Import({RepositoryConfig.class})
public class ServiceConfig {
}
