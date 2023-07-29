package com.tulski.coffeemachine


import spock.lang.Specification

import static com.tulski.coffeemachine.DrinkMakerCommand.DrinkOrder

class PricingSpec extends Specification {

    def pricing = new Pricing()

    def "one tea is 0,4 euro"() {
        given: "order for 1 tea"
        def order = new DrinkOrder(DrinkType.TEA, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(40)
    }

    def "one coffee is 0,6 euro"() {
        given: "order for 1 coffee"
        def order = new DrinkOrder(DrinkType.COFFEE, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(60)
    }

    def "one chocolate is 0,5 euro"() {
        given: "order for 1 chocolate"
        def order = new DrinkOrder(DrinkType.CHOCOLATE, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(50)
    }

    def "one orange juice is 0,6 euro"() {
        given: "order for 1 orange juice"
        def order = new DrinkOrder(DrinkType.ORANGE_JUICE, 0, false)

        when: "calculating price"
        def price = pricing.calculateTotalPriceFor(order)

        then: "price should be correct"
        price == Money.cents(60)
    }

    def "sugar should be free"() {
        given: "order for 1 coffee withou sugar"
        def without = new DrinkOrder(DrinkType.COFFEE, 2, false)

        and: "order for 1 coffee with sugar"
        def with = new DrinkOrder(DrinkType.COFFEE, 0, false)

        expect: "prices should be the same"
        pricing.calculateTotalPriceFor(without) == pricing.calculateTotalPriceFor(with)
    }

    def "stick should be free"() {
        given: "order for 1 coffee without stick"
        def without = new DrinkOrder(DrinkType.COFFEE, 0, false)

        and: "order for 1 coffee with stick"
        def with = new DrinkOrder(DrinkType.COFFEE, 0, true)

        expect: "prices should be the same"
        pricing.calculateTotalPriceFor(without) == pricing.calculateTotalPriceFor(with)
    }
}
