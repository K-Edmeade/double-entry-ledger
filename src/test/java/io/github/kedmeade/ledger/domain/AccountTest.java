package io.github.kedmeade.ledger.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Currency;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AccountTest {

    private static final Currency GBP = Currency.getInstance("GBP");

    @ParameterizedTest
    @MethodSource("validAccount")
    void shouldCreateValidAccount(AccountId accountId, String accountName, AccountType accountType, Currency currency) {
        Account account = new Account(accountId, accountName, accountType, currency);

        assertThat(account.id()).isEqualTo(accountId);
        assertThat(account.name()).isEqualTo(accountName);
        assertThat(account.type()).isEqualTo(accountType);
        assertThat(account.currency()).isEqualTo(currency);
    }

    @Test
    void shouldNotAllowNullId() {
        assertThatThrownBy(() -> new Account(null, "Accounts Receivable", AccountType.ASSET, GBP))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Account ID cannot be null");
    }

    @Test
    void shouldNotAllowNullName() {
        assertThatThrownBy(() -> new Account(AccountId.newId(), null, AccountType.ASSET, GBP))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Account name cannot be null");
    }

    @Test
    void shouldNotAllowEmptyName() {
        assertThatThrownBy(() -> new Account(AccountId.newId(), "", AccountType.ASSET, GBP))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account name cannot be blank");
    }

    @Test
    void shouldNotAllowBlankName() {
        assertThatThrownBy(() -> new Account(AccountId.newId(), "   ", AccountType.ASSET, GBP))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account name cannot be blank");
    }

    @Test
    void shouldNotAllowNullType() {
        assertThatThrownBy(() -> new Account(AccountId.newId(), "Accounts Receivable", null, GBP))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Account type cannot be null");
    }

    @Test
    void shouldNotAllowNullCurrency() {
        assertThatThrownBy(() -> new Account(AccountId.newId(), "Accounts Receivable", AccountType.ASSET, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Account currency cannot be null");
    }

    static Stream<Arguments> validAccount() {
        return Stream.of(
                Arguments.of(AccountId.newId(), "Accounts Receivable", AccountType.ASSET, GBP),
                Arguments.of(AccountId.newId(), "Accounts Payable", AccountType.LIABILITY, GBP));
    }
}
