package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder;

import java.util.ArrayList;
import java.util.List;

class MemorableConsoleNotificationSender implements UserNotifier {

    private final List<Money> missingMoneyHistory = new ArrayList<>();
    private final List<DrinkOrder> completedOrdersHistory = new ArrayList<>();

    @Override
    public void notEnoughMoneyProvided(Money missingMoney) {
        System.out.println("Not enough money provided: " + missingMoney.toString());
        missingMoneyHistory.add(missingMoney);
    }

    @Override
    public void orderCompleted(DrinkOrder drinkOrder) {
        System.out.println("Drink " + drinkOrder.drinkType().toString() + " is served");
        completedOrdersHistory.add(drinkOrder);
    }

    public List<Money> getMissingMoneyHistory() {
        return missingMoneyHistory;
    }

    public List<DrinkOrder> getCompletedOrdersHistory() {
        return completedOrdersHistory;
    }

}
