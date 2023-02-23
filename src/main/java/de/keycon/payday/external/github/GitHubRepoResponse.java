package de.keycon.payday.external.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubRepoResponse {

    @JsonProperty("stargazers_count")
    private int stars;
}
