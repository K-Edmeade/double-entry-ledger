package io.github.kedmeade.ledger.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Currency;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    void moneyWithSameAmountAndCurrencyAreEqual() {
        Money money1 = new Money(100, Currency.getInstance("GBP"));
        Money money2 = new Money(100, Currency.getInstance("GBP"));

        assert money1.equals(money2);
        assert money1.hashCode() == money2.hashCode();
    }

    @Test
    void nullCurrencyThrowsException() {
        assertThrows(NullPointerException.class, () -> new Money(100, null));
    }
}
