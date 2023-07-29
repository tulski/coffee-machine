package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder;

public interface UserNotifier {
    void notEnoughMoneyProvided(Money missingMoney);

    void orderCompleted(DrinkOrder order);
}
