package de.keycon.payday.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "balance")
public class BalanceEntity {

    @Id
    @SequenceGenerator(name = "balance_seq", sequenceName = "seq_balance_id", allocationSize = 1)
    @GeneratedValue(generator = "balance_seq")
    private Long id;

    private double balance;

    private double alreadyTransferred;
}
