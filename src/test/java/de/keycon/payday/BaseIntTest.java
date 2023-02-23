package de.keycon.payday;

import com.github.tomakehurst.wiremock.client.WireMock;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
public class BaseIntTest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    protected String baseUrl;

    @PostConstruct
    void init() {
        baseUrl = "http://localhost:" + port;
    }

    @AfterEach
    void cleanup() {
        WireMock.reset();
        clearDatabase();
    }

    protected void clearDatabase() {
        flyway.clean();
        flyway.migrate();
    }
}
