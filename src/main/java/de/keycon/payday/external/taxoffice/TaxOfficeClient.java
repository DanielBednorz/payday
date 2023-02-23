package de.keycon.payday.external.taxoffice;

import de.keycon.payday.service.Balance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class TaxOfficeClient {

    private final WebClient taxOfficeWebClient;
    private final TaxOfficeProperties taxOfficeProperties;

    public void sendBalanceReport(Balance balance) {
        taxOfficeWebClient.post()
                .uri(taxOfficeProperties.getPath().getBalanceReport())
                .body(BodyInserters.fromValue(balance))
                .retrieve()
                .toEntity(Void.class).block();
    }

}
