package de.keycon.payday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PaydayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaydayApplication.class, args);
    }
}