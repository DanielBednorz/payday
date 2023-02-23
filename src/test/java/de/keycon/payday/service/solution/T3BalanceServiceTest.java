package de.keycon.payday.service.solution;

import de.keycon.payday.service.BalanceService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class T3BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @ParameterizedTest
    @MethodSource("calculateBalanceTestData")
    void calculateBalanceWith0stars(double stars, double expectedBalance) {

        double actualBalance = balanceService.calculateTotalBalance(stars);

        assertThat(actualBalance).isEqualTo(expectedBalance);
    }

    private static Stream<Arguments> calculateBalanceTestData() {
        //given stars, expected balance
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 1),
                Arguments.of(2, 4),
                Arguments.of(3, 6),
                Arguments.of(4, 16),
                Arguments.of(5, 20),
                Arguments.of(6, 48),
                Arguments.of(7, 56),
                Arguments.of(8, 128)
        );
    }

}
