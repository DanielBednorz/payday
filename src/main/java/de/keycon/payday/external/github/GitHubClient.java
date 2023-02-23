package de.keycon.payday.external.github;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final WebClient githubWebClient;
    private final GithubProperties githubProperties;

    public int getStars() {
        String url = githubProperties.getPath().getRepos() + "/" + githubProperties.getProject();
        GitHubRepoResponse response = sendRequest(url).getBody();
        return response == null ? 0 : response.getStars();
    }

    private ResponseEntity<GitHubRepoResponse> sendRequest(String url) {
        return githubWebClient.get()
                .uri(url)
                .retrieve()
                .toEntity(GitHubRepoResponse.class).block();
    }

}
