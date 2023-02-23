package de.keycon.payday.service.solution;

import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import de.keycon.payday.service.Balance;
import de.keycon.payday.service.BalanceMapper;
import de.keycon.payday.service.BalanceMapperImpl;
import de.keycon.payday.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class T2BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Spy
    @SuppressWarnings("unused")
    private BalanceMapper balanceMapper = new BalanceMapperImpl();

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void calculateBalance1() {
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(10);
        balanceEntity.setAlreadyTransferred(5);
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balanceEntity));

        Balance actualBalance = balanceService.getBalance();

        assertThat(actualBalance.getBalance()).isEqualTo(10);
        assertThat(actualBalance.getAlreadyTransferred()).isEqualTo(5);
    }

}
