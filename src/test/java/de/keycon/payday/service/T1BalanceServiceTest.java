package de.keycon.payday.service;

import de.keycon.payday.external.github.GitHubClient;
import de.keycon.payday.external.taxoffice.TaxOfficeClient;
import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class T1BalanceServiceTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceMapper balanceMapper;

    @Mock
    private TaxOfficeClient taxOfficeClient;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void calculateBalance1() {
        when(gitHubClient.getStars()).thenReturn(2);
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(0);
        when(balanceRepository.findById(any())).thenReturn(Optional.of(balanceEntity));
        Balance balance = new Balance();
        balance.setBalance(11);
        balance.setAlreadyTransferred(1);
        when(balanceMapper.toBalance(any())).thenReturn(balance);
        Balance actualBalance = balanceService.calculateBalance();
        assertThat(actualBalance.getBalance()).isEqualTo(14);
        assertThat(actualBalance.getAlreadyTransferred()).isEqualTo(4);
        verify(taxOfficeClient, times(1)).sendBalanceReport(any());
    }

}
