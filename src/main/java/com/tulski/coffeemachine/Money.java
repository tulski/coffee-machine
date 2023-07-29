package com.tulski.coffeemachine;

public class Money {

    private final int value;

    public static Money zero() {
        return cents(0);
    }

    public static Money cents(int cents) {
        return new Money(cents);
    }

    public static Money euros(int dollars) {
        return cents(dollars * 100);
    }

    private Money(int value) {
        this.value = value;
    }

    public Money add(Money other) {
        return cents(this.value + other.value);
    }

    public Money subtract(Money other) {
        return cents(this.value - other.value);
    }

    public boolean isLessThan(Money other) {
        return this.value < other.value;
    }

    public boolean equals(Object other) {
        if (other instanceof Money) {
            return this.value == ((Money) other).value;
        }
        return false;
    }

    public String toString() {
        return String.format("%d.%02d", this.value / 100, this.value % 100) + "€.";
    }

}
