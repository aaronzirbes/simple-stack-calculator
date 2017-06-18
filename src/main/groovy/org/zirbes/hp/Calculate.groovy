package org.zirbes.hp

import groovy.transform.CompileStatic

@CompileStatic
class Calculate {

    static void run() {
        Calculator calc = new Calculator()

        calc.push('4')
        calc.push('3')
        calc.push('+')
        double result = calc.pop()
        println "4 + 3 = ${result}"
    }

}
