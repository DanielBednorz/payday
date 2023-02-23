package de.keycon.payday.service.solution;

import de.keycon.payday.external.github.GitHubClient;
import de.keycon.payday.external.taxoffice.TaxOfficeClient;
import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import de.keycon.payday.service.BalanceMapper;
import de.keycon.payday.service.BalanceMapperImpl;
import de.keycon.payday.service.BalanceService;
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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class T0BalanceServiceTest {

    @Mock
    @SuppressWarnings("unused")
    private TaxOfficeClient taxOfficeClient;

    @Spy
    @SuppressWarnings("unused")
    private BalanceMapper balanceMapper = new BalanceMapperImpl();

    @Mock
    private GitHubClient gitHubClient;

    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private BalanceService balanceService;

    @ParameterizedTest(name = "Balance for {2} stars is {3}")
    @MethodSource("getBalanceTestData")
    void test(double balanceBefore, double alreadyTransferredBefore, int stars, double balanceAfter, double alreadyTransferredAfter) {
        //given
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(balanceBefore);
        balanceEntity.setAlreadyTransferred(alreadyTransferredBefore);
        when(gitHubClient.getStars()).thenReturn(stars);
        when(balanceRepository.findById(1L)).thenReturn(Optional.of(balanceEntity));

        //when
        var actualBalance = balanceService.calculateBalance();

        //then
        assertThat(actualBalance.getBalance()).isEqualTo(balanceAfter);
        assertThat(actualBalance.getAlreadyTransferred()).isEqualTo(alreadyTransferredAfter);
    }

    private static Stream<Arguments> getBalanceTestData() {
        return Stream.of(
                Arguments.of(14, 4, 3, 16, 6),
                Arguments.of(26, 16, 5, 30, 20)
        );
    }
}
