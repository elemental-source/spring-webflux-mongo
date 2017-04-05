package com.example;

import com.example.model.Person;
import com.example.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class DemoApplication {

    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadGroupName("DemoThreadGroup");
        executor.setThreadNamePrefix("DemoThreadPrefix");
        executor.setCorePoolSize(5000);
        return executor;
    }

	@Bean
	CommandLineRunner initData(PersonRepository repository) {
		return (p) -> {
			repository.deleteAll().block();
			for(int i = 1; i <= 10000; i++) {
				repository.save(new Person(String.valueOf(i), "Test")).block();
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
