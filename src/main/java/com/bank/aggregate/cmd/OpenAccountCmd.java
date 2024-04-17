package com.bank.aggregate.cmd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountCmd {

    @TargetAggregateIdentifier
    private String id;

    private String userId;

    private Long initialBalance;
}
