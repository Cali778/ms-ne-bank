package com.bank.aggregate.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountOpennedEvent {

    private String id;

    private String userId;

    private Long initialBalance;
}
