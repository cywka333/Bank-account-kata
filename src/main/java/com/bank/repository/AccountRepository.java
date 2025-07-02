package com.bank.repository;

import com.bank.domain.Account;

import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public Account findById(String accountId){
        Account account = accounts.get(accountId);
        if(account == null){
            account = new Account(accountId);
            accounts.put(accountId, account);
        }
        return account;
    }

    public void save (Account account){
        accounts.put(account.getAccountId(), account);
    }
}
