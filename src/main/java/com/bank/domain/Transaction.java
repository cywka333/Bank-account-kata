package com.bank.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private final LocalDateTime dateTime;
    private final Money amount;
    private final TransactionType type;
    private final Money balanceAfter;

    public Transaction(LocalDateTime dateTime, Money amount, TransactionType type, Money balanceAfter) {
        this.dateTime = dateTime;
        this.amount = amount;
        this.type = type;
        this.balanceAfter = balanceAfter;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Money getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return dateTime.equals(that.dateTime) && amount.equals(that.amount) && type == that.type && balanceAfter.equals(that.balanceAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, amount, type, balanceAfter);
    }
}
