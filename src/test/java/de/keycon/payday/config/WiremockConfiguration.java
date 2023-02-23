package de.keycon.payday.config;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiremockConfiguration {

    @Bean
    public WireMockConfigurationCustomizer optionsCustomizer() {
        return config -> {
            config.withRootDirectory("resources/wiremock");
            config.port(5555);
            config.notifier(new ConsoleNotifier(true));
        };
    }
}
