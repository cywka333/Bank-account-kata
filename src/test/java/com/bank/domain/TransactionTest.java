package com.bank.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void shouldCreateTransaction(){
        LocalDateTime date = LocalDateTime.now();
        Money amount = new Money("200.00");
        Money balanceAfter = new Money("200.00");
        TransactionType type = TransactionType.valueOf("DEPOSIT");

        Transaction transaction = new Transaction(date,amount,type, balanceAfter);

        assertThat(transaction.getDateTime()).isEqualTo(date);
        assertThat(transaction.getAmount()).isEqualTo(amount);
        assertThat(transaction.getType()).isEqualTo(type);
        assertThat(transaction.getBalanceAfter()).isEqualTo(balanceAfter);
    }

}