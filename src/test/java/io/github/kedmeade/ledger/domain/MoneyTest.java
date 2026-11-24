package io.github.kedmeade.ledger.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.kedmeade.ledger.exception.CurrencyMismatchException;
import java.util.Currency;
import org.junit.jupiter.api.Test;

class MoneyTest {

    private static final Currency GBP = Currency.getInstance("GBP");

    @Test
    void moneyWithSameAmountAndCurrencyAreEqual() {
        Money money1 = new Money(100, GBP);
        Money money2 = new Money(100, GBP);

        assertThat(money1).isEqualTo(money2).hasSameHashCodeAs(money2);
    }

    @Test
    void nullCurrencyThrowsException() {
        assertThrows(NullPointerException.class, () -> new Money(100, null));
    }

    @Test
    void addGivenTwoMoneyObjectsWithSameCurrencyReturnsCorrectSum() {

        Money money1 = new Money(100, GBP);
        Money money2 = new Money(200, GBP);
        Money expected = new Money(300, GBP);

        Money actual = money1.add(money2);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void addGivenTwoMoneyObjectsWithDifferentCurrenciesThrowsException() {
        Money money1 = new Money(100, GBP);
        Money money2 = new Money(200, Currency.getInstance("USD"));

        assertThrows(CurrencyMismatchException.class, () -> money1.add(money2));
    }

    @Test
    void subtractGivenTwoMoneyObjectsWithSameCurrencyReturnsCorrectDifference() {

        Money money1 = new Money(200, GBP);
        Money money2 = new Money(150, GBP);
        Money expected = new Money(50, GBP);

        Money actual = money1.subtract(money2);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtractGivenTwoMoneyObjectsWithSameCurrencyReturnsCorrectNegativeDifference() {

        Money money1 = new Money(100, GBP);
        Money money2 = new Money(200, GBP);
        Money expected = new Money(-100, GBP);

        Money actual = money1.subtract(money2);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtractGivenTwoMoneyObjectsWithDifferentCurrenciesThrowsException() {
        Money money1 = new Money(200, GBP);
        Money money2 = new Money(100, Currency.getInstance("USD"));

        assertThrows(CurrencyMismatchException.class, () -> money1.subtract(money2));
    }

    @Test
    void addingOverflowingAmountsThrowsException() {
        Money money1 = new Money(Long.MAX_VALUE, GBP);
        Money money2 = new Money(1, GBP);

        assertThrows(ArithmeticException.class, () -> money1.add(money2));
    }

    @Test
    void addingTwoToMaxMinusOneThrows() {
        Money money1 = new Money(Long.MAX_VALUE - 1, GBP);
        Money money2 = new Money(2, GBP);

        assertThrows(ArithmeticException.class, () -> money1.add(money2));
    }

    @Test
    void addingTwoLargeAmountsThatOverflowThrow() {
        Money money1 = new Money(Long.MAX_VALUE - 40000000, GBP);
        Money money2 = new Money(50000000, GBP);

        assertThrows(ArithmeticException.class, () -> money1.add(money2));
    }

    @Test
    void addingZeroToMaxValueReturnsMaxValue() {
        Money money1 = new Money(Long.MAX_VALUE, GBP);
        Money money2 = new Money(0, GBP);

        Money expected = new Money(Long.MAX_VALUE, GBP);

        Money actual = money1.add(money2);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void subtractOverflowingAmountsThrowsException() {
        Money money1 = new Money(Long.MIN_VALUE, GBP);
        Money money2 = new Money(1, GBP);

        assertThrows(ArithmeticException.class, () -> money1.subtract(money2));
    }

    @Test
    void subtractingNegativeOneFromMaxThrows() {
        Money money1 = new Money(Long.MAX_VALUE, GBP);
        Money money2 = new Money(-1, GBP);

        assertThrows(ArithmeticException.class, () -> money1.subtract(money2));
    }

    @Test
    void subtractTwoToMinPlusOneThrowsException() {
        Money money1 = new Money(Long.MIN_VALUE + 1, GBP);
        Money money2 = new Money(2, GBP);

        assertThrows(ArithmeticException.class, () -> money1.subtract(money2));
    }

    @Test
    void negateMinValueThrowsException() {
        Money money = new Money(Long.MIN_VALUE, GBP);

        assertThrows(ArithmeticException.class, money::negate);
    }
}
