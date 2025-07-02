package com.bank.util;

import com.bank.domain.Money;
import com.bank.domain.Transaction;
import com.bank.domain.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatementFormatterTest {

    private StatementFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new StatementFormatter();
    }

    @Test
    void shouldFormatSingleDeposit(){
        Transaction transaction = new Transaction(
                LocalDateTime.of(2024,1,10,10,0),
                new Money("1000.00"),
                TransactionType.DEPOSIT,
                new Money("1000.00")
        );

        String result = formatter.format(Collections.singletonList(transaction));

        assertThat(result).contains("DATE | AMOUNT | BALANCE");
        assertThat(result).contains("10/01/2024 | 1000.00 | 1000.00");
    }

    @Test
    void shouldFormatEmptyTransactionList() {
        String result = formatter.format(Collections.emptyList());

        assertThat(result).isEqualTo("DATE | AMOUNT | BALANCE");
    }

    @Test
    void shouldFormatWithdrawalWithNegativeSign() {
        Transaction transaction = new Transaction(
                LocalDateTime.of(2024, 1, 14, 10, 0),
                new Money("500.00"),
                TransactionType.WITHDRAWAL,
                new Money("2500.00")
        );

        String result = formatter.format(Collections.singletonList(transaction));

        assertThat(result).contains("14/01/2024 | -500.00 | 2500.00");
    }

    @Test
    void shouldFormatMultipleTransactions() {
        Transaction deposit1 = new Transaction(
                LocalDateTime.of(2024, 1, 10, 10, 0),
                new Money("1000.00"),
                TransactionType.DEPOSIT,
                new Money("1000.00")
        );
        Transaction deposit2 = new Transaction(
                LocalDateTime.of(2024, 1, 13, 10, 0),
                new Money("2000.00"),
                TransactionType.DEPOSIT,
                new Money("3000.00")
        );
        Transaction withdrawal = new Transaction(
                LocalDateTime.of(2024, 1, 14, 10, 0),
                new Money("500.00"),
                TransactionType.WITHDRAWAL,
                new Money("2500.00")
        );

        String result = formatter.format(Arrays.asList(deposit1, deposit2, withdrawal));

        String expected = "DATE | AMOUNT | BALANCE\n" +
                "10/01/2024 | 1000.00 | 1000.00\n" +
                "13/01/2024 | 2000.00 | 3000.00\n" +
                "14/01/2024 | -500.00 | 2500.00";

        assertThat(result).isEqualTo(expected);
    }
}