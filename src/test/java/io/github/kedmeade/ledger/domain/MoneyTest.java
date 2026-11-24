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

    @Test
    void addGivenTwoMoneyObjectsWithSameCurrencyReturnsCorrectSum() {

        Money money1 = new Money(100, Currency.getInstance("GBP"));
        Money money2 = new Money(200, Currency.getInstance("GBP"));
        Money expected = new Money(300, Currency.getInstance("GBP"));

        Money actual = money1.add(money2);

        assert actual.equals(expected);
    }

    @Test
    void addGivenTwoMoneyObjectsWithDifferentCurrenciesThrowsException() {
        Money money1 = new Money(100, Currency.getInstance("GBP"));
        Money money2 = new Money(200, Currency.getInstance("USD"));

        assertThrows(IllegalArgumentException.class, () -> money1.add(money2));
    }

    @Test
    void minusGivenTwoMoneyObjectsWithSameCurrencyReturnsCorrectDifference() {

        Money money1 = new Money(200, Currency.getInstance("GBP"));
        Money money2 = new Money(150, Currency.getInstance("GBP"));
        Money expected = new Money(50, Currency.getInstance("GBP"));

        Money actual = money1.subtract(money2);

        assert actual.equals(expected);
    }

    @Test
    void minusGivenTwoMoneyObjectsWithSameCurrencyReturnsCorrectNegativeDifference() {

        Money money1 = new Money(100, Currency.getInstance("GBP"));
        Money money2 = new Money(200, Currency.getInstance("GBP"));
        Money expected = new Money(-100, Currency.getInstance("GBP"));

        Money actual = money1.subtract(money2);

        assert actual.equals(expected);
    }

    @Test
    void minusGivenTwoMoneyObjectsWithDifferentCurrenciesThrowsException() {
        Money money1 = new Money(200, Currency.getInstance("GBP"));
        Money money2 = new Money(100, Currency.getInstance("USD"));

        assertThrows(IllegalArgumentException.class, () -> money1.subtract(money2));
    }

    @Test
    void addingOverflowingAmountsThrowsException() {
        Money money1 = new Money(Long.MAX_VALUE, Currency.getInstance("GBP"));
        Money money2 = new Money(1, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, () -> money1.add(money2));
    }

    @Test
    void addingOverflowingAmountsThrowsException1() {
        Money money1 = new Money(Long.MAX_VALUE - 1, Currency.getInstance("GBP"));
        Money money2 = new Money(2, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, () -> money1.add(money2));
    }

    @Test
    void addingOverflowingAmountsThrowsException2() {
        Money money1 = new Money(Long.MAX_VALUE - 40000000, Currency.getInstance("GBP"));
        Money money2 = new Money(50000000, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, () -> money1.add(money2));
    }

    @Test
    void addingZeroToOverflowingAmountThrowsReturnsMaxValue() {
        Money money1 = new Money(Long.MAX_VALUE, Currency.getInstance("GBP"));
        Money money2 = new Money(0, Currency.getInstance("GBP"));

        Money expected = new Money(Long.MAX_VALUE, Currency.getInstance("GBP"));

        Money actual = money1.add(money2);

        assert actual.equals(expected);
    }

    @Test
    void subtractingOverflowingAmountsThrowsException() {
        Money money1 = new Money(Long.MIN_VALUE, Currency.getInstance("GBP"));
        Money money2 = new Money(1, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, () -> money1.subtract(money2));
    }

    @Test
    void subtractingOverflowingAmountsThrowsException1() {
        Money money1 = new Money(Long.MAX_VALUE, Currency.getInstance("GBP"));
        Money money2 = new Money(-1, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, () -> money1.subtract(money2));
    }

    @Test
    void subtractingOverflowingAmountsThrowsException2() {
        Money money1 = new Money(Long.MIN_VALUE + 1, Currency.getInstance("GBP"));
        Money money2 = new Money(2, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, () -> money1.subtract(money2));
    }

    @Test
    void negateMinValueThrowsException() {
        Money money = new Money(Long.MIN_VALUE, Currency.getInstance("GBP"));

        assertThrows(ArithmeticException.class, money::negate);
    }
}
