package org.zirbes.hp

import groovy.transform.CompileStatic

@CompileStatic
interface StackCalculator {

    void enter(String entry)

    double peek()

    double pop()

    void clear()

    boolean isEmpty()

    int size()

}
