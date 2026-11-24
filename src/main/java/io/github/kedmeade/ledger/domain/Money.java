package io.github.kedmeade.ledger.domain;

import io.github.kedmeade.ledger.exception.CurrencyMismatchException;
import java.util.Currency;
import java.util.Objects;

public record Money(long amountMinor, Currency currency) {

    public Money {
        Objects.requireNonNull(currency, "Currency cannot be null");
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(Math.addExact(amountMinor, other.amountMinor), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(Math.subtractExact(amountMinor, other.amountMinor), currency);
    }


    public Money negate() {
        return new Money(Math.negateExact(amountMinor), currency());
    }

    private void requireSameCurrency(Money money) {
        if (!currency.equals(money.currency)) {
            throw new CurrencyMismatchException(currency, money.currency);
        }
    }
}
