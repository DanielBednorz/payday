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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceServiceBackupTest {

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private BalanceRepository balanceRepository;

    @Spy
    private BalanceMapper balanceMapper = new BalanceMapperImpl();

    @Mock
    @SuppressWarnings("unused")
    private TaxOfficeClient taxOfficeClient;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void calculateBalance() {
        // given
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(0);
        when(gitHubClient.getStars()).thenReturn(0);
        when(balanceRepository.findById(any())).thenReturn(Optional.of(balanceEntity));
        var balance = new Balance();
        balance.setBalance(0);
        when(balanceMapper.toBalance(any())).thenReturn(balance);

        // when
        Balance actualBalance = balanceService.calculateBalance();

        // then
        assertThat(actualBalance.getBalance()).isEqualTo(0);
        assertThat(actualBalance.getAlreadyTransferred()).isEqualTo(0);
    }

    @ParameterizedTest
    @MethodSource("calculateBalanceData")
    void calculateBalance(int stars, double alreadyTransferred, double balanceBefore, double expectedBalance) {
        // given
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(balanceBefore);
        balanceEntity.setAlreadyTransferred(alreadyTransferred);
        when(gitHubClient.getStars()).thenReturn(stars);
        when(balanceRepository.findById(any())).thenReturn(Optional.of(balanceEntity));

        // when
        Balance actualBalance = balanceService.calculateBalance();

        // then
        assertThat(actualBalance.getBalance()).isEqualTo(expectedBalance);
    }

    private static Stream<Arguments> calculateBalanceData() {
        //stars, alreadyTransferred, balanceBefore, expectedBalance
        return Stream.of(
                Arguments.of(0, 0, 10, 10),
                Arguments.of(1, 0, 10, 11),
                Arguments.of(2, 1, 11, 14),
                Arguments.of(3, 4, 14, 16),
                Arguments.of(4, 6, 16, 26),
                Arguments.of(5, 16, 26, 30),
                Arguments.of(6, 20, 0, 28),
                Arguments.of(7, 48, 28, 36),
                Arguments.of(8, 56, 36, 108)
        );
    }


    @ParameterizedTest
    @MethodSource("calculateTotalBalanceData")
    void calculateTotalBalance(int stars, double expectedTotalBalance) {
        // when
        double actualTotalBalance = balanceService.calculateTotalBalance(stars);

        // then
        assertThat(actualTotalBalance).isEqualTo(expectedTotalBalance);
    }

    private static Stream<Arguments> calculateTotalBalanceData() {
        //stars, expectedTotalBalance
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 1),
                Arguments.of(3, 6),
                Arguments.of(8, 128)
        );
    }

}

