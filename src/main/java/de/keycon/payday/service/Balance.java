package de.keycon.payday.service;

import lombok.Data;

@Data
public class Balance {

    private double balance = 0;
    private double lastTransfer = 0;
    private double alreadyTransferred = 0;
}
