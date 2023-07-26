package com.tulski.coffeemachine

import spock.lang.Specification

class MainSpec extends Specification {

    def "green test"() {
        expect:
        true
    }

    def "red test"() {
        expect:
        false
    }
}
