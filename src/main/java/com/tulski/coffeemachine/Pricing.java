package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder;

class Pricing {

    public Money calculateTotalPriceFor(DrinkOrder order) {
        return switch (order.drinkType()) {
            case TEA -> Money.cents(40);
            case CHOCOLATE -> Money.cents(50);
            case COFFEE -> Money.cents(60);
        };
    }

}
