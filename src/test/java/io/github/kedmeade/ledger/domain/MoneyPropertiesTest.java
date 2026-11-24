package io.github.kedmeade.ledger.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Currency;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.LongRange;

class MoneyPropertiesTest {

    private static final Currency GBP = Currency.getInstance("GBP");

    @Property
    void addingThenSubtractingReturnsOriginal(
            @ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long a,
            @ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long b) {

        Money original = new Money(a, GBP);
        Money other = new Money(b, GBP);

        assertThat(original.add(other).subtract(other)).isEqualTo(original);
    }

    @Property
    void addingisCommutative(
            @ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long a,
            @ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long b) {

        Money original = new Money(a, GBP);
        Money other = new Money(b, GBP);

        assertThat(original.add(other).equals(other.add(original))).isTrue();
    }

    @Property
    void addingZeroReturnsOriginal(@ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long a) {

        Money original = new Money(a, GBP);
        Money other = new Money(0, GBP);

        assertThat(original.add(other)).isEqualTo(original);
    }

    @Property
    void negatingTwiceReturnsOriginal(@ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long a) {

        Money original = new Money(a, GBP);

        assertThat(original.negate().negate()).isEqualTo(original);
    }

    @Property
    void subtractingIsAddingTheNegative(
            @ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long a,
            @ForAll @LongRange(min = -1_000_000_000_000L, max = 1_000_000_000_000L) long b) {

        Money original = new Money(a, GBP);
        Money other = new Money(b, GBP);

        assertThat(original.subtract(other)).isEqualTo(original.add(other.negate()));
    }
}
