package io.github.kedmeade.ledger.domain;

import java.util.Currency;
import java.util.Objects;

public record Money(long amountMinor, Currency currency) {

    public Money {
        Objects.requireNonNull(currency, "Currency cannot be null");
    }
}
