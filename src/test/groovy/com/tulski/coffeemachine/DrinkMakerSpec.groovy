package com.tulski.coffeemachine

import spock.lang.Specification

import static com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder

class DrinkMakerSpec extends Specification {

    def pricing = new Pricing()
    def userNotifier = new MemorableConsoleNotificationSender()
    def managementReporter = new ConsoleManagementReporter()
    def drinkMaker = new DrinkMaker(pricing, userNotifier, managementReporter)

    def setup() {
        drinkMaker.withdrawAllMoney()
        managementReporter.clearHistory()
        userNotifier.clearHistory()
    }

    def "drink maker should make drink if correct amount of money is given"() {
        given: "order for 1 extra hot tea with 1 sugar and stick"
        def order = new DrinkOrder(DrinkType.TEA, 1, true, true)

        when: "drink maker receives 1 euro"
        drinkMaker.putMoney(Money.euros(1))

        and: "submit"
        def result = drinkMaker.makeDrink(order)

        then: "should make drink"
        result.present
        result.get().type() == DrinkType.TEA
        result.get().sugar() == 1
        result.get().stick()
        result.get().extraHot()
    }

    def "drink maker should not make drink if not enough money is given"() {
        given: "order for 1 tea"
        def command = new DrinkOrder(DrinkType.TEA)

        when: "drink maker receives 0.2 euro"
        drinkMaker.putMoney(Money.cents(20))

        and: "submit order"
        def result = drinkMaker.makeDrink(command)

        then: "should not make drink"
        result.empty
    }

    def "drink maker should allow to put extra money if missing"() {
        given: "order for 1 tea with 1 sugar and stick"
        def order = new DrinkOrder(DrinkType.TEA)

        when: "drink maker receives 0.1 euro"
        drinkMaker.putMoney(Money.cents(10))

        and: "user submits order"
        drinkMaker.makeDrink(order)

        then: "user is notified about missing money"
        userNotifier.missingMoneyHistory.last() == Money.cents(30)

        when: "user puts extra money"
        drinkMaker.putMoney(Money.cents(30))

        and: "user submits order"
        def result = drinkMaker.makeDrink(order)

        then: "should make drink"
        result.present
        result.get().type() == DrinkType.TEA
    }

    def "drink maker should notify customer if not enough money is given"() {
        given: "order for tea"
        def order = new DrinkOrder(DrinkType.TEA, 1)

        when: "drink maker receives 0.1 euro"
        drinkMaker.putMoney(Money.cents(10))

        and: "user submits order"
        drinkMaker.makeDrink(order)

        then: "should notify customer"
        userNotifier.missingMoneyHistory.last() == Money.cents(30)
    }

    def "management should know about completed order"() {
        given: "order for 1 tea"
        def order = new DrinkOrder(DrinkType.TEA)
        def orderPrice = pricing.calculateTotalPriceFor(order)
        def userMoney = Money.euros(10)

        when: "user submits order without putting money"
        drinkMaker.makeDrink(order)

        then: "management reporter should not have any completed orders"
        managementReporter.completedDrinkOrdersHistory.size() == 0

        when: "user puts money"
        drinkMaker.putMoney(userMoney)

        and: "user submits order again"
        drinkMaker.makeDrink(order)

        then: "management reporter should have 1 completed order with correct details"
        managementReporter.completedDrinkOrdersHistory.size() == 1
        managementReporter.completedDrinkOrdersHistory.last().drinkType() == DrinkType.TEA
        managementReporter.completedDrinkOrdersHistory.last().sugarQuantity() == 0
        !managementReporter.completedDrinkOrdersHistory.last().stick()
        !managementReporter.completedDrinkOrdersHistory.last().extraHot()
        managementReporter.completedDrinkOrdersHistory.last().price() == pricing.calculateTotalPriceFor(order)
        managementReporter.completedDrinkOrdersHistory.last().balance() == userMoney.subtract(orderPrice)
    }
}
