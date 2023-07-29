package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder;

import java.time.LocalDateTime;

record CompletedDrinkOrder(
        DrinkType drinkType,
        int sugarQuantity,
        boolean stick,
        boolean extraHot,
        Money price,
        Money balance,
        LocalDateTime date
) {

    static CompletedDrinkOrder of(DrinkOrder order, Money price, Money balanceAfter) {
        return new CompletedDrinkOrder(
                order.drinkType(),
                order.sugarQuantity(),
                order.stick(),
                order.extraHot(),
                price,
                balanceAfter,
                LocalDateTime.now()
        );
    }

}
