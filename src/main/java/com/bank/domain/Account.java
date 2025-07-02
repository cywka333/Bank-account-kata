package com.bank.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final String accountId;
    private final List<Transaction> transactions;

    public Account(String accountId) {
        this.accountId = accountId;
        this.transactions = new ArrayList<>();
    }

    public void deposit(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Cannot deposit negative amount");
        }

        Money newBalance = getBalance().add(amount);
        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                amount,
                TransactionType.DEPOSIT,
                newBalance
        );
        transactions.add(transaction);
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        }

        Money currentBalance = getBalance();
        if (amount.isGreaterThan(currentBalance)) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + ". Current balance: " + currentBalance
            );
        }

        Money newBalance = currentBalance.substruct(amount);
        Transaction transaction = new Transaction(
                LocalDateTime.now(),
                amount,
                TransactionType.WITHDRAWAL,
                newBalance
        );
        transactions.add(transaction);
    }

    public Money getBalance() {
        if (transactions.isEmpty()) {
            return new Money("0.00");
        }
        return transactions.get(transactions.size() - 1).getBalanceAfter();
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public String getAccountId() {
        return accountId;
    }
}
