package com.bank.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account("ACCOUNT-000");
    }

    @Test
    void shouldStartWithZeroBalance(){
        assertThat(account.getBalance()).isEqualTo(new Money("0.00"));
    }

    @Test
    void shouldIncreaseBalanceOnDeposit() {
        account.deposit(new Money("100.00"));

        assertThat(account.getBalance()).isEqualTo(new Money("100.00"));
    }

    @Test
    void shouldAccumulateMultipleDeposits() {
        account.deposit(new Money("100.00"));
        account.deposit(new Money("50.00"));

        assertThat(account.getBalance()).isEqualTo(new Money("150.00"));
    }

    @Test
    void shouldDecreaseBalanceOnWithdrawal() {
        account.deposit(new Money("100.00"));

        account.withdraw(new Money("30.00"));

        assertThat(account.getBalance()).isEqualTo(new Money("70.00"));
    }

    @Test
    void shouldThrowExceptionWhenWithdrawingMoreThanBalance() {
        account.deposit(new Money("100.00"));

        assertThatThrownBy(() -> account.withdraw(new Money("150.00")))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Cannot withdraw 150.00. Current balance: 100.00");
    }

    @Test
    void shouldNotAllowNegativeDeposit() {
        assertThatThrownBy(() -> account.deposit(new Money("-50.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot deposit negative amount");
    }

    @Test
    void shouldNotAllowNegativeWithdrawal() {
        assertThatThrownBy(() -> account.withdraw(new Money("-50.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot withdraw negative amount");
    }

    @Test
    void shouldRecordTransactionHistory() {
        account.deposit(new Money("100.00"));
        account.withdraw(new Money("30.00"));

        assertThat(account.getTransactions()).hasSize(2);
        assertThat(account.getTransactions().get(0).getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(account.getTransactions().get(1).getType()).isEqualTo(TransactionType.WITHDRAWAL);
    }
}