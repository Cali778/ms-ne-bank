package com.bank.aggregate.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountMoneyWithdrawnEvent {

    private String id;

    private Long amount;
}
