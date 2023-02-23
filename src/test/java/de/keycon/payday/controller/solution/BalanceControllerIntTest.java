package de.keycon.payday.controller.solution;

import de.keycon.payday.BaseIntTest;
import de.keycon.payday.controller.BalanceDto;
import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static de.keycon.payday.GitHubStubs.stubGitHubStars;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class BalanceControllerIntTest extends BaseIntTest {

    @Autowired
    private BalanceRepository balanceRepository;

    @Test
    void happyCase() {
        var actualBalance = RestAssured.with()
                .get(baseUrl + "/balance")
                .then().statusCode(200)
                .extract().as(BalanceDto.class);
        assertThat(actualBalance.getBalance()).isEqualTo(20);
    }

    @Test
    void combinedScenario() {
        //this test will reveal a bug where a second save of a balance will not update the database but create a new entry
        //the cause is the erased database-id while mapping the entity to the model data class

        //given
        setBalance(10);

        stubGitHubStars(3);
        sendGetBalanceRequest();

        stubGitHubStars(5);
        sendGetBalanceRequest();

        setBalance(0);
        stubGitHubStars(8);

        //when
        var actualBalance = sendGetBalanceRequest();

        //then
        assertThat(actualBalance.getBalance()).isEqualTo(108);
    }

    private void setBalance(double newBalance) {
        var balanceEntity = balanceRepository.findById(1L);
        if (balanceEntity.isPresent()) {
            balanceEntity.get().setBalance(newBalance);
            balanceRepository.save(balanceEntity.get());
        } else {
            balanceRepository.save(new BalanceEntity(1L, newBalance, 0));
        }
    }

    private BalanceDto sendGetBalanceRequest() {
        return RestAssured.with()
                .get(baseUrl + "/balance")
                .then().statusCode(200)
                .extract().as(BalanceDto.class);
    }

}
