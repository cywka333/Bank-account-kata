package com.bank.util;

import com.bank.domain.Transaction;
import com.bank.domain.TransactionType;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class StatementFormatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String HEADER = "DATE | AMOUNT | BALANCE";

    public String format(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return HEADER;
        }

        String transactionLines = transactions.stream()
                .map(this::formatTransaction)
                .collect(Collectors.joining("\n"));

        return HEADER + "\n" + transactionLines;
    }

    private String formatTransaction(Transaction transaction) {
        String date = transaction.getDateTime().format(DATE_FORMATTER);
        String amount = formatAmount(transaction);
        String balance = transaction.getBalanceAfter().toString();

        return String.format("%s | %s | %s", date, amount, balance);
    }

    private String formatAmount(Transaction transaction) {
        String amountStr = transaction.getAmount().toString();
        if (transaction.getType() == TransactionType.WITHDRAWAL) {
            return "-" + amountStr;
        }
        return amountStr;
    }

}