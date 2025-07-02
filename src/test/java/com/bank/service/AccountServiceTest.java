package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.repository.AccountRepository;
import com.bank.util.StatementFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService service;
    private AccountRepository repository;

    @BeforeEach
    void setUp(){
        repository = new AccountRepository();
        StatementFormatter formatter = new StatementFormatter();
        service = new AccountService(repository, formatter);
    }

    @Test
    void shouldDepositMoney(){
        service.deposit("ACCOUNT-001", new BigDecimal("100.00"));

        Account account = repository.findById("ACCOUNT-001");
        assertThat(account.getBalance().toString()).isEqualTo("100.00");
    }

    @Test
    void shouldWithdrawMoney(){
        service.deposit("ACCOUNT-001", new BigDecimal("100.00"));
        service.withdraw("ACCOUNT-001", new BigDecimal("20.00"));

        Account account = repository.findById("ACCOUNT-001");
        assertThat(account.getBalance().toString()).isEqualTo("80.00");
    }

    @Test
    void shouldThrowExceptionOnInsufficientFunds() {
        service.deposit("ACCOUNT-001", new BigDecimal("100.00"));

        assertThatThrownBy(() -> service.withdraw("ACCOUNT-001", new BigDecimal("150.00")))
                .isInstanceOf(InsufficientFundsException.class);
    }

    @Test
    void shouldGetFormattedStatement(){
        service.deposit("ACCOUNT-001", new BigDecimal("1000.00"));
        service.deposit("ACCOUNT-001", new BigDecimal("2000.00"));
        service.withdraw("ACCOUNT-001", new BigDecimal("500.00"));

        String statement = service.getStatement("ACCOUNT-001");

        assertThat(statement).contains("DATE | AMOUNT | BALANCE");
        assertThat(statement).contains("1000.00");
        assertThat(statement).contains("2000.00");
        assertThat(statement).contains("500.00");
        assertThat(statement).contains("2500.00");

    }

}