package com.tulski.coffeemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ConsoleManagementReporter implements ManagementReporter {

    private final List<CompletedDrinkOrder> completedDrinkOrders = new ArrayList<>();

    @Override
    public void collect(CompletedDrinkOrder completedDrinkOrder) {
        completedDrinkOrders.add(completedDrinkOrder);
    }

    public List<CompletedDrinkOrder> getCompletedDrinkOrdersHistory() {
        return completedDrinkOrders;
    }

    public void clearHistory() {
        completedDrinkOrders.clear();
    }

    @Override
    public void report() {
        var totalMoney = Money.zero();
        var drinkTypeCount = new HashMap<DrinkType, Integer>();

        for (var completedDrinkOrder : completedDrinkOrders) {
            totalMoney = totalMoney.add(completedDrinkOrder.price());
            var drinkType = completedDrinkOrder.drinkType();
            drinkTypeCount.put(drinkType, drinkTypeCount.getOrDefault(drinkType, 0) + 1);
        }
        System.out.println("Total money earned: " + totalMoney.toString());
        System.out.println("Number of drinks sold:");
        for (var drinkType : drinkTypeCount.keySet()) {
            System.out.println(drinkType.toString() + ": " + drinkTypeCount.get(drinkType));
        }
    }
}
