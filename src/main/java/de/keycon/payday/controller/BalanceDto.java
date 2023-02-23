package de.keycon.payday.controller;

import lombok.Data;

@Data
public class BalanceDto {

    private double balance = 0;
    private double alreadyTransferred = 0;
}
