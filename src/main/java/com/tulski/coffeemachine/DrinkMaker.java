package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder;

import java.util.Optional;

class DrinkMaker {

    private final UserNotifier userNotifier;
    private final Pricing pricing;
    private Money balance;

    public DrinkMaker(UserNotifier userNotifier, Pricing pricing) {
        this.userNotifier = userNotifier;
        this.pricing = pricing;
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
        var price = pricing.calculateTotalPriceFor(order);
        if (balance.isLessThan(price)) {
            userNotifier.notEnoughMoneyProvided(price.subtract(balance));
            return Optional.empty();
        }
        balance.subtract(price);
        var drink = new Drink(order.drinkType(), order.sugarQuantity(), order.stick());
        userNotifier.orderCompleted(order);
        return Optional.of(drink);
    }

}
