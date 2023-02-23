package de.keycon.payday.service;

import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class T2BalanceServiceTest {

    @MockBean
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceService balanceService;

    @Test
    void calculateBalance() {
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(10);
        balanceEntity.setAlreadyTransferred(5);
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balanceEntity));

        Balance actualBalance = balanceService.getBalance();

        assertThat(actualBalance.getBalance()).isEqualTo(10);
        assertThat(actualBalance.getAlreadyTransferred()).isEqualTo(5);
    }

}
