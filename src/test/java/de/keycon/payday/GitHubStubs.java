package de.keycon.payday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.keycon.payday.external.github.GitHubRepoResponse;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class GitHubStubs {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void stubGitHubStars(int stars) {
        String responseBody;
        try {
            responseBody = objectMapper.writeValueAsString(new GitHubRepoResponse(stars));
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
    }
}
