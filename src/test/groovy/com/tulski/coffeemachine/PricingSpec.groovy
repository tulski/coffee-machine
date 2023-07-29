package com.tulski.coffeemachine


import spock.lang.Specification

class PricingSpec extends Specification {

    def pricing = new Pricing()

    def "one tea is 0,4 euro"() {
        given: "order for 1 tea"
        def order = new DrinkMakerCommand.DrinkOrder(DrinkType.TEA, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(40)
    }

    def "one coffee is 0,6 euro"() {
        given: "order for 1 coffee"
        def order = new DrinkMakerCommand.DrinkOrder(DrinkType.COFFEE, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(60)
    }

    def "one chocolate is 0,5 euro"() {
        given: "order for 1 chocolate"
        def order = new DrinkMakerCommand.DrinkOrder(DrinkType.CHOCOLATE, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(50)
    }

    def "sugar should be free"() {
        given: "order for 1 coffee withou sugar"
        def without = new DrinkMakerCommand.DrinkOrder(DrinkType.COFFEE, 2, false)

        and: "order for 1 coffee with sugar"
        def with = new DrinkMakerCommand.DrinkOrder(DrinkType.COFFEE, 0, false)

        expect: "prices should be the same"
        pricing.calculateTotalPriceFor(without) == pricing.calculateTotalPriceFor(with)
    }

    def "stick should be free"() {
        given: "order for 1 coffee withou stick"
        def without = new DrinkMakerCommand.DrinkOrder(DrinkType.COFFEE, 0, false)

        and: "order for 1 coffee with stick"
        def with = new DrinkMakerCommand.DrinkOrder(DrinkType.COFFEE, 0, true)

        expect: "prices should be the same"
        pricing.calculateTotalPriceFor(without) == pricing.calculateTotalPriceFor(with)
    }
}
