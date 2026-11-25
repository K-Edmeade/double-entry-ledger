package io.github.kedmeade.ledger.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AccountTypeTest {

    @Test
    void assetNormalBalanceReturnsDebit() {
        assertThat(AccountType.ASSET.normalBalance()).isEqualTo(Side.DEBIT);
    }

    @Test
    void expenseNormalBalanceReturnsDebit() {
        assertThat(AccountType.EXPENSE.normalBalance()).isEqualTo(Side.DEBIT);
    }

    @Test
    void liabilityNormalBalanceReturnsCredit() {
        assertThat(AccountType.LIABILITY.normalBalance()).isEqualTo(Side.CREDIT);
    }

    @Test
    void incomeNormalBalanceReturnsCredit() {
        assertThat(AccountType.INCOME.normalBalance()).isEqualTo(Side.CREDIT);
    }

    @Test
    void equityNormalBalanceReturnsCredit() {
        assertThat(AccountType.EQUITY.normalBalance()).isEqualTo(Side.CREDIT);
    }

    @Test
    void allAccountTypesHaveNormalBalance() {
        for (AccountType accountType : AccountType.values()) {
            assertThat(accountType.normalBalance()).isNotNull();
        }
    }
}
