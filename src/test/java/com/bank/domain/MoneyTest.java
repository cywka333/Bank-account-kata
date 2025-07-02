package com.bank.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneyFromBigDecimal(){
        Money money = new Money(new BigDecimal("100.00"));
        assertThat(money.getAmount()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    void shouldCreateMoneyFromString(){
        Money money = new Money("124.00");
        assertThat(money.getAmount()).isEqualTo(new BigDecimal("124.00"));
    }

    @Test
    void shouldAddMoney(){
        Money money1 = new Money("160.00");
        Money money2 = new Money("220.00");

        Money result = money1.add(money2);

        assertThat(result).isEqualTo(new Money("380.00"));
    }

    @Test
    void shouldSubstructMoney(){
        Money money1 = new Money("100.00");
        Money money2 = new Money("60.00");

        Money result = money1.substruct(money2);

        assertThat(result).isEqualTo(new Money("40.00"));
    }

    @Test
    void shouldCompareMoneyAmounts(){
        Money money1 = new Money("100.00");
        Money money2 = new Money("60.00");

        assertThat(money1.isGreaterThan(money2)).isTrue();
        assertThat(money2.isGreaterThan(money1)).isFalse();
    }

    @Test
    void shouldCheckIfMoneyIsNegative(){
        Money positiveMoney = new Money("100.00");
        Money negativeMoney = new Money("-60.00");

        assertThat(positiveMoney.isNegative()).isFalse();
        assertThat(negativeMoney.isNegative()).isTrue();
    }
}
