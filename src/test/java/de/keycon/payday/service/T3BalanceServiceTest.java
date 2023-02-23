package de.keycon.payday.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class T3BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void calculateBalanceWith0stars() {

        double actualBalance = balanceService.calculateTotalBalance(0);

        assertThat(actualBalance).isEqualTo(0);
    }

    @Test
    void calculateBalanceWith1stars() {

        double actualBalance = balanceService.calculateTotalBalance(1);

        assertThat(actualBalance).isEqualTo(1);
    }

    @Test
    void calculateBalanceWith2stars() {

        double actualBalance = balanceService.calculateTotalBalance(2);

        assertThat(actualBalance).isEqualTo(4);
    }

    @Test
    void calculateBalanceWith3stars() {

        double actualBalance = balanceService.calculateTotalBalance(3);

        assertThat(actualBalance).isEqualTo(6);
    }

    @Test
    void calculateBalanceWith4stars() {

        double actualBalance = balanceService.calculateTotalBalance(4);

        assertThat(actualBalance).isEqualTo(16);
    }

    @Test
    void calculateBalanceWith5stars() {

        double actualBalance = balanceService.calculateTotalBalance(5);

        assertThat(actualBalance).isEqualTo(20);
    }

    @Test
    void calculateBalanceWith6stars() {

        double actualBalance = balanceService.calculateTotalBalance(6);

        assertThat(actualBalance).isEqualTo(48);
    }

    @Test
    void calculateBalanceWith7stars() {

        double actualBalance = balanceService.calculateTotalBalance(7);

        assertThat(actualBalance).isEqualTo(56);
    }

    @Test
    void calculateBalanceWith8stars() {

        double actualBalance = balanceService.calculateTotalBalance(8);

        assertThat(actualBalance).isEqualTo(128);
    }

}
