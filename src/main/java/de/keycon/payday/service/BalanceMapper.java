package de.keycon.payday.service;

import de.keycon.payday.controller.BalanceDto;
import de.keycon.payday.repository.BalanceEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BalanceMapper {

    BalanceEntity toBalanceEntity(Balance balance);

    Balance toBalance(BalanceEntity balanceEntity);

    BalanceDto toBalanceDto(Balance balance);

}
