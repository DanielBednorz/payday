package de.keycon.payday.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import de.keycon.payday.external.github.GitHubRepoResponse;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
public class BalanceControllerIntTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void happyCase() {
        String responseBody;
        try {
            responseBody = objectMapper.writeValueAsString(new GitHubRepoResponse(5));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        stubFor(get(urlPathMatching("/repos/DanielBednorz/payday"))
                .atPriority(1)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody(responseBody)
                )
        );
        var actualBalance = RestAssured.with()
                .get(baseUrl + "/balance")
                .then().statusCode(200)
                .extract().as(BalanceDto.class);
        assertThat(actualBalance.getBalance()).isEqualTo(20);
    }
}
