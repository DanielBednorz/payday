package de.keycon.payday.external.taxoffice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "external.taxoffice")
@Data
public class TaxOfficeProperties {

    private String baseUrl;
    private Path path = new Path();

    @Data
    public static class Path {
        private String balanceReport;
    }
}
