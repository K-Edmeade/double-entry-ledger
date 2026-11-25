package io.github.kedmeade.ledger.domain;

public enum AccountType {
    ASSET,
    LIABILITY,
    EQUITY,
    INCOME,
    EXPENSE;

    public Side normalBalance() {
        return switch (this) {
            case ASSET, EXPENSE -> Side.DEBIT;
            case LIABILITY, EQUITY, INCOME -> Side.CREDIT;
        };
    }
}
