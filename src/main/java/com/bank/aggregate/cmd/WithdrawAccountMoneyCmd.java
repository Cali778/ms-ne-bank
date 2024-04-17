package com.bank.aggregate.cmd;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
public class WithdrawAccountMoneyCmd {

    @TargetAggregateIdentifier
    private String id;

    private Long amount;
}
