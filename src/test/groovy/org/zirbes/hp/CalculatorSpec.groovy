package org.zirbes.hp

import spock.lang.Specification
import spock.lang.Unroll

class CalculatorSpec extends Specification {

    Calculator calculator

    void setup() {
        calculator = new Calculator()
    }

    void 'can calculate things'() {
        when:
        keys.each { calculator.enter(it) }

        then:
        calculator.pop() == expected

        where:
        keys                                  | expected
        ['4', '9', '+' ]                      | 13
        ['3', '9', '-' ]                      | -6
        ['6', '9', '*' ]                      | 54
        ['9', '3', '/' ]                      | 3
        ['9', '2', '3', '+', '-', '10', '*' ] | 40
    }

}
