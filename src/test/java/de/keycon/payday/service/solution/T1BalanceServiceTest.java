package de.keycon.payday.service.solution;

import de.keycon.payday.external.github.GitHubClient;
import de.keycon.payday.external.taxoffice.TaxOfficeClient;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class T1BalanceServiceTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private BalanceRepository balanceRepository;

    @Spy
    @SuppressWarnings("unused")
    private BalanceMapper balanceMapper = new BalanceMapperImpl();

    @Mock
    private TaxOfficeClient taxOfficeClient;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void calculateBalance1() {
        //given
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(11);
        balanceEntity.setAlreadyTransferred(1);
        when(gitHubClient.getStars()).thenReturn(2);
        when(balanceRepository.findById(any())).thenReturn(Optional.of(balanceEntity));

        //when
        Balance actualBalance = balanceService.calculateBalance();

        //then
        assertThat(actualBalance.getBalance()).isEqualTo(14);
        assertThat(actualBalance.getAlreadyTransferred()).isEqualTo(4);
        verify(taxOfficeClient).sendBalanceReport(any());
    }

}
