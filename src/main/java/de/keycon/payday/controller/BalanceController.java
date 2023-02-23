package de.keycon.payday.controller;

import de.keycon.payday.service.BalanceMapper;
import de.keycon.payday.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
class BalanceController {

    private final BalanceService balanceService;
    private final BalanceMapper balanceMapper;

    @GetMapping(path = "", produces = "application/json")
    private BalanceDto getBalance() {
        var balance = balanceService.calculateBalance();
        return balanceMapper.toBalanceDto(balance);
    }

}

