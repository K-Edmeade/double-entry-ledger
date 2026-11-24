package io.github.kedmeade.ledger.exception;

import java.util.Currency;

public class CurrencyMismatchException extends RuntimeException {
    private static final String MESSAGE = "Cannot operate on Money with different currencies: %s and %s";

    public CurrencyMismatchException(Currency currency1, Currency currency2) {
        super(String.format(MESSAGE, currency1, currency2));
    }
}
