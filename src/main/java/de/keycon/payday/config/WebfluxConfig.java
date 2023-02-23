package de.keycon.payday.config;

import de.keycon.payday.external.github.GithubProperties;
import de.keycon.payday.external.taxoffice.TaxOfficeProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebfluxConfig {

    @Bean(name = "githubWebClient")
    public WebClient githubWebClient(GithubProperties properties) {
        return WebClient.create(properties.getBaseUrl());
    }

    @Bean(name = "taxOfficeWebClient")
    public WebClient taxOfficeWebClient(TaxOfficeProperties properties) {
        return WebClient.create(properties.getBaseUrl());
    }
}
