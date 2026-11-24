package io.github.kedmeade.ledger.domain;

import io.github.kedmeade.ledger.exception.CurrencyMismatchException;
import java.util.Currency;
import java.util.Objects;

public record Money(long amountMinor, Currency currency) {

    public Money {
        Objects.requireNonNull(currency, "Currency cannot be null");
    }

    public Money add(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot add Money with different currencies");
        }

        return new Money(Math.addExact(amountMinor, money.amountMinor), currency);
    }

    public Money subtract(Money money) {
        if (!currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot subtract Money with different currencies");
        }

        return new Money(Math.subtractExact(amountMinor, money.amountMinor()), currency());
    }

    public Money negate() {
        return new Money(Math.negateExact(amountMinor), currency());
    }

    public void validateCurrency(Money money) {
        if (!currency.equals(money.currency)) {
            throw new CurrencyMismatchException(currency, money.currency);
        }
    }
}
