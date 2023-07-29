package com.tulski.coffeemachine

import spock.lang.Specification

import static com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder

class DrinkMakerSpec extends Specification {

    def notificationSender = new MemorableConsoleNotificationSender()
    def pricing = new Pricing()
    def drinkMaker = new DrinkMaker(notificationSender, pricing)

    def setup() {
        drinkMaker.withdrawAllMoney()
    }

    def "drink maker should make drink if correct amount of money is given"() {
        given: "order for 1 tea with 1 sugar and stick"
        def order = new DrinkOrder(DrinkType.TEA, 1, true)

        when: "drink maker receives 1 euro"
        drinkMaker.putMoney(Money.euros(1))

        and: "submit"
        def result = drinkMaker.makeDrink(order)

        then: "should make drink"
        result.present
        result.get().type() == DrinkType.TEA
        result.get().sugar() == 1
        result.get().stick()
    }

    def "drink maker should not make drink if not enough money is given"() {
        given: "order for 1 tea with 1 sugar and stick"
        def command = new DrinkOrder(DrinkType.TEA, 1, true)

        when: "drink maker receives 0.2 euro"
        drinkMaker.putMoney(Money.cents(20))

        and: "submit order"
        def result = drinkMaker.makeDrink(command)

        then: "should not make drink"
        result.empty
    }

    def "drink maker should allow to put extra money if missing"() {
        given: "order for 1 tea with 1 sugar and stick"
        def order = new DrinkOrder(DrinkType.TEA, 1, true)

        when: "drink maker receives 0.2 euro"
        drinkMaker.putMoney(Money.cents(10))

        and: "user submits order"
        drinkMaker.makeDrink(order)

        then: "user is notified about missing money"
        notificationSender.missingMoneyHistory.last() == Money.cents(30)

        when: "user puts extra money"
        drinkMaker.putMoney(Money.cents(30))

        and: "user submits order"
        def result = drinkMaker.makeDrink(order)

        then: "should make drink"
        result.present
        result.get().type() == DrinkType.TEA
        result.get().sugar() == 1
        result.get().stick()
    }

    def "drink maker should notify customer if not enough money is given"() {
        given: "order for 1 tea with 1 sugar and stick"
        def order = new DrinkOrder(DrinkType.TEA, 1, true)

        when: "drink maker receives 0.5 euro"
        drinkMaker.putMoney(Money.cents(10))

        and: "user submits order"
        drinkMaker.makeDrink(order)

        then: "should notify customer"
        notificationSender.missingMoneyHistory.last() == Money.cents(30)

    }
}
