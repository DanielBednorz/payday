package de.keycon.payday.service;

import de.keycon.payday.external.github.GitHubClient;
import de.keycon.payday.external.taxoffice.TaxOfficeClient;
import de.keycon.payday.repository.BalanceEntity;
import de.keycon.payday.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceService {

    private final GitHubClient gitHubClient;
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final TaxOfficeClient taxOfficeClient;

    public Balance calculateBalance() {
        double stars = gitHubClient.getStars();
        double totalBalance = calculateTotalBalance(stars);
        Balance balance = calculateDiffBalance(totalBalance);
        saveBalance(balance);
        taxOfficeClient.sendBalanceReport(balance);
        return balance;
    }

    public double calculateTotalBalance(double stars) {
        return stars * Math.pow(2, (stars - stars % 2) / 2);
    }

    protected Balance calculateDiffBalance(double totalBalance) {
        Balance balance = getBalance();
        double diffAmount = totalBalance - balance.getAlreadyTransferred();
        balance.setLastTransfer(diffAmount);
        balance.setBalance(balance.getBalance() + diffAmount);
        balance.setAlreadyTransferred(balance.getAlreadyTransferred() + diffAmount);
        return balance;
    }

    private void saveBalance(Balance balance) {
        balanceRepository.save(balanceMapper.toBalanceEntity(balance));
    }

    public Balance getBalance() {
        BalanceEntity balanceEntity = balanceRepository.findById(1L).orElse(new BalanceEntity());
        return balanceMapper.toBalance(balanceEntity);
    }


    //for T4-Test only
    public List<Double> calculateTotalBalanceSequence(List<Double> stars) {
        return stars.stream().map(this::calculateTotalBalance).collect(Collectors.toList());
    }

}
