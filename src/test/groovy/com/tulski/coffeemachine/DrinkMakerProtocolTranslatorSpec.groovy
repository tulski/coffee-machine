package com.tulski.coffeemachine

import spock.lang.Specification

class DrinkMakerProtocolTranslatorSpec extends Specification {

    def translator = new DrinkMakerProtocolTranslator()

    def "should translate order for 1 tea with 1 sugar and stick"() {
        given: "T:1:1 command"
        def command = "T:1:1"

        when: "translating command"
        var result = translator.translate(command) as DrinkMakerCommand.DrinkOrder

        then: "result should be correct"
        result.drinkType() == DrinkType.TEA
        result.sugarQuantity() == 1
        result.stick()
    }

    def "should translate order for 1 chocolate with no sugar and no stick"() {
        given: "H:: command"
        def command = "H::"

        when: "translating command"
        var result = translator.translate(command) as DrinkMakerCommand.DrinkOrder

        then: "result should be correct"
        result.drinkType() == DrinkType.CHOCOLATE
        result.sugarQuantity() == 0
        !result.stick()
    }

    def "should translate order for 1 coffee with 2 sugars and stick"() {
        given: "C:2:1 command"
        def command = "C:2:1"

        when: "translating command"
        var result = translator.translate(command) as DrinkMakerCommand.DrinkOrder

        then: "result should be correct"
        result.drinkType() == DrinkType.COFFEE
        result.sugarQuantity() == 2
        result.stick()
    }

    def "should translate command with message"() {
        given: "M:message-content command"
        def command = "M:message-content"

        when: "translating command"
        def result = translator.translate(command)

        then: "result should be correct"
        result == new DrinkMakerCommand.NotifyCustomer("message-content")
    }

    def "should fail when sugar is greater than 2"() {
        when: "translating command"
        translator.translate("T:3:1")

        then: "result should be null"
        thrown(IllegalArgumentException)
    }

    def "should fail on invalid command"() {
        when: "translating command"
        translator.translate(command)

        then: "result should be null"
        thrown(IllegalArgumentException)

        where:
        command << [null, "", " ", "invalid command", "S:invalid command", "Mmessage-content", "T:1:1:1"]
    }
}
