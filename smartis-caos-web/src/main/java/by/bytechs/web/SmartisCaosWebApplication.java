package by.bytechs.web;

import by.bytechs.service.config.ServiceConfig;
import by.bytechs.web.controllers.message.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Import({ServiceConfig.class})
@EnableAsync
public class SmartisCaosWebApplication {
	@Autowired
	private MessageListener messageListener;

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {SmartisCaosWebApplication.class, ServiceConfig.class}, args);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Bean
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
		return args -> executor.execute(messageListener);
	}
}
