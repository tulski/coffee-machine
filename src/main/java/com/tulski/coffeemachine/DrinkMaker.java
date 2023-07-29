package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder;

import java.util.Optional;

class DrinkMaker {

    private final Pricing pricing;
    private final UserNotifier userNotifier;
    private final ManagementReporter managementReporter;
    private Money balance;

    public DrinkMaker(Pricing pricing, UserNotifier userNotifier, ManagementReporter managementReporter) {
        this.pricing = pricing;
        this.userNotifier = userNotifier;
        this.managementReporter = managementReporter;
        this.balance = Money.zero();
    }

    public void putMoney(Money money) {
        balance = balance.add(money);
    }

    public Money withdrawAllMoney() {
        var money = balance;
        balance = Money.zero();
        return money;
    }

    public Optional<Drink> makeDrink(DrinkOrder order) {
        var orderValue = pricing.calculateTotalPriceFor(order);
        if (balance.isLessThan(orderValue)) {
            userNotifier.notEnoughMoneyProvided(orderValue.subtract(balance));
            return Optional.empty();
        }

        balance = balance.subtract(orderValue);
        
        var drink = new Drink(order.drinkType(), order.sugarQuantity(), order.stick(), order.extraHot());
        var completedOrder = CompletedDrinkOrder.of(order, orderValue, balance);

        userNotifier.orderCompleted(order);
        managementReporter.collect(completedOrder);

        return Optional.of(drink);
    }

}
