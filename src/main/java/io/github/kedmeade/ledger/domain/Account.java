package io.github.kedmeade.ledger.domain;

import java.util.Currency;
import java.util.Objects;

public record Account(AccountId id, String name, AccountType type, Currency currency) {

    public Account {
        Objects.requireNonNull(id, "Account ID cannot be null");
        Objects.requireNonNull(name, "Account name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Account name cannot be blank");
        }
        Objects.requireNonNull(type, "Account type cannot be null");
        Objects.requireNonNull(currency, "Account currency cannot be null");
    }
}
