package io.github.kedmeade.ledger.domain;

import java.util.Objects;

public record Postings(AccountId accountId, Side side, Money amount) {

    public Postings {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        Objects.requireNonNull(side, "Posting side cannot be null");
        Objects.requireNonNull(amount, "Posting amount cannot be null");
        if (amount.amountMinor() <= 0) {
            throw new IllegalArgumentException("Posting amount must be greater than zero");
        }
    }

    public static Postings debit(AccountId accountId, Money amount) {
        return new Postings(accountId, Side.DEBIT, amount);
    }

    public static Postings credit(AccountId accountId, Money amount) {
        return new Postings(accountId, Side.CREDIT, amount);
    }
}
