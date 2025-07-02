package com.bank.service;

import com.bank.domain.Account;
import com.bank.domain.Money;
import com.bank.repository.AccountRepository;
import com.bank.util.StatementFormatter;

import java.math.BigDecimal;

public class AccountService {
    private final AccountRepository repository;
    private final StatementFormatter formatter;

    public AccountService(AccountRepository repository, StatementFormatter formatter) {
        this.repository = repository;
        this.formatter = formatter;
    }

    public void deposit(String accountId, BigDecimal amount){
        Account account = repository.findById(accountId);
        account.deposit(new Money(amount));
        repository.save(account);
    }

    public void withdraw(String accountId, BigDecimal amount){
        Account account = repository.findById(accountId);
        account.withdraw(new Money(amount));
        repository.save(account);
    }

    public String getStatement(String accountId){
        Account account = repository.findById(accountId);
        return formatter.format(account.getTransactions());
    }


}
