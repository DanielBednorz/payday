package de.keycon.payday.service;

import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class T4BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private BalanceMapper balanceMapper;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void assertTrue() {
        balanceService.calculateTotalBalance(0);
        assert (true);
    }

    @Test
    void assertThatWrongUsage() {
        var actualBalance = balanceService.calculateTotalBalance(1);
        assertThat(actualBalance == 0);
    }

    @Test
    void mistakenParameters() {
        var expectedBalance = 0;
        var actualBalance = balanceService.calculateTotalBalance(0);
        Assertions.assertEquals(actualBalance, expectedBalance);
    }

    @Test
    void testingAStub() {
        var balance = new Balance();
        balance.setBalance(99);
        when(balanceRepository.findById(1L)).thenReturn(Optional.empty());
        when(balanceMapper.toBalance(any())).thenReturn(balance);

        var actualBalance = balanceService.getBalance();

        assertThat(actualBalance.getBalance()).isEqualTo(99);
    }

    @Test
    void unspecificMatcher() {
        var balance = new BalanceEntity();
        balance.setBalance(99);
        when(balanceRepository.findById(any())).thenReturn(Optional.of(balance));

        var actualBalance = balanceService.getBalance();

        assertThat(actualBalance).isNull();
    }

    @Test
    void explicitAssertListItem() {
        var stars = List.of(0.0, 1.0, 2.0, 3.0, 4.0);

        var actualTotalBalances = balanceService.calculateTotalBalanceSequence(stars);

        assertThat(actualTotalBalances.size()).isEqualTo(5);
        assertThat(actualTotalBalances.get(0)).isEqualTo(0.0);
        assertThat(actualTotalBalances.get(1)).isEqualTo(1.0);
        assertThat(actualTotalBalances.get(2)).isEqualTo(4.0);
        assertThat(actualTotalBalances.get(3)).isEqualTo(6.0);
        assertThat(actualTotalBalances.get(4)).isEqualTo(16.0);
    }

    @Test
    void unsuitableValues() {
        var email = "email";
        var stars = 123456.0;
        var mobileNumber = "123456";
        var name = "Max Mustermann";

        var s = 2.0;
        var be = new BalanceEntity();
        var balance = new BalanceEntity();
        var repo = balanceRepository;
        var cud = balanceService;
    }

}
