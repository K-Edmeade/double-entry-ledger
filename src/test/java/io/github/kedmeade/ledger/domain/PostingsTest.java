package io.github.kedmeade.ledger.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Currency;
import org.junit.jupiter.api.Test;

class PostingsTest {

    private static final Currency GBP = Currency.getInstance("GBP");
    private static final Money AMOUNT = new Money(100, GBP);

    @Test
    void postingWithValidFieldsIsCreated() {
        AccountId accountId = AccountId.newId();

        Postings posting = new Postings(accountId, Side.DEBIT, AMOUNT);

        assertThat(posting.accountId()).isEqualTo(accountId);
        assertThat(posting.side()).isEqualTo(Side.DEBIT);
        assertThat(posting.amount()).isEqualTo(AMOUNT);
    }

    @Test
    void postingWithDebitGivesPostingWithDebitSide() {
        Postings posting = Postings.debit(AccountId.newId(), AMOUNT);

        assertThat(posting.side()).isEqualTo(Side.DEBIT);
        assertThat(posting.side()).isNotEqualTo(Side.CREDIT);
    }

    @Test
    void postingWithCreditGivesPostingWithCreditSide() {
        Postings posting = Postings.credit(AccountId.newId(), AMOUNT);

        assertThat(posting.side()).isEqualTo(Side.CREDIT);
        assertThat(posting.side()).isNotEqualTo(Side.DEBIT);
    }

    @Test
    void postingZeroAmountThrowsException() {
        assertThatThrownBy(() -> new Postings(AccountId.newId(), Side.DEBIT, new Money(0, GBP)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Posting amount must be greater than zero");
    }

    @Test
    void postingNegativeAmountThrowsException() {
        assertThatThrownBy(() -> new Postings(AccountId.newId(), Side.DEBIT, new Money(-100, GBP)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Posting amount must be greater than zero");
    }

    @Test
    void postingOnePenceAmountIsValid() {
        Postings posting = new Postings(AccountId.newId(), Side.DEBIT, new Money(1, GBP));

        assertThat(posting.amount().amountMinor()).isEqualTo(1);
    }

    @Test
    void postingWithNullAccountIdThrowsException() {
        assertThatThrownBy(() -> new Postings(null, Side.DEBIT, AMOUNT))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Account ID cannot be null");
    }

    @Test
    void postingWithNullSideThrowsException() {
        assertThatThrownBy(() -> new Postings(AccountId.newId(), null, AMOUNT))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Posting side cannot be null");
    }

    @Test
    void postingWithNullAmountThrowsException() {
        assertThatThrownBy(() -> new Postings(AccountId.newId(), Side.DEBIT, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Posting amount cannot be null");
    }
}
