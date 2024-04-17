package com.bank.aggregate;

import com.bank.aggregate.cmd.DepositAccountMoneyCmd;
import com.bank.aggregate.cmd.OpenAccountCmd;
import com.bank.aggregate.cmd.WithdrawAccountMoneyCmd;
import com.bank.aggregate.event.AccountOpennedEvent;
import com.bank.aggregate.event.AccountMoneyDepositedEvent;
import com.bank.aggregate.event.AccountMoneyWithdrawnEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;

@Getter
@Aggregate
@Slf4j
public class BankAggregate {

    @AggregateIdentifier
    private String id;

    private String userId;

    private Long balance;

    private String status;


    public BankAggregate() {
    }

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    private void handle(OpenAccountCmd cmd) {
        AccountOpennedEvent event = AccountOpennedEvent.builder()
                .id(cmd.getId())
                .userId(cmd.getUserId())
                .initialBalance(cmd.getInitialBalance())
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public AccountMoneyWithdrawnEvent handle(WithdrawAccountMoneyCmd cmd){

        if (!"ACTIVO".equals(this.status)){
            throw new RuntimeException("NO PUEDES RETIRAR DINERO");
        }
        if(cmd.getAmount()>this.balance){
            throw new RuntimeException("SALDO INSUFICIENTE");
        }
        AccountMoneyWithdrawnEvent event = new AccountMoneyWithdrawnEvent().builder()
                .id(cmd.getId())
                .amount(cmd.getAmount())
                .build();
        AggregateLifecycle.apply(event);
        return event;
    }

    @CommandHandler
    public AccountMoneyDepositedEvent handle(DepositAccountMoneyCmd cmd){
        AccountMoneyDepositedEvent event = new AccountMoneyDepositedEvent().builder()
                .id(cmd.getId())
                .amount(cmd.getAmount())
                .build();
        AggregateLifecycle.apply(event);
        return event;
    }

    @EventSourcingHandler
    public void onEvent(AccountOpennedEvent event) {
        this.id = event.getId();
        this.userId = event.getUserId();
        this.balance = event.getInitialBalance();
        this.status = "ACTIVO";
    }

    @EventSourcingHandler
    public void onEvent(AccountMoneyWithdrawnEvent event){
        log.info("DineroCuentaRetiradoEvent: "+event.getAmount());
        this.balance = this.balance - event.getAmount();
    }

    @EventSourcingHandler
    public void onEvent(AccountMoneyDepositedEvent event){
        log.info("CuentaDineroDepositadoEvent: "+event.getAmount());
        this.balance =  this.balance + event.getAmount();
    }
}
