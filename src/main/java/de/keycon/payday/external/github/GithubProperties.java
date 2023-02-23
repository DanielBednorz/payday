package de.keycon.payday.external.github;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "external.github")
@Data
public class GithubProperties {

    public String project;
    private String baseUrl;
    private Path path = new Path();

    @Data
    public static class Path {
        private String repos;
    }
}
