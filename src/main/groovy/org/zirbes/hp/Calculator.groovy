package org.zirbes.hp

import groovy.transform.CompileStatic

@CompileStatic
class Calculator implements StackCalculator {

    protected Stack<Double> stack

    protected static final List<String> operators = [ '+', '-', '*', '/' ]

    Calculator() {
        stack = new Stack<Double>()
    }

    /** Precondition (double or operator).
     * Button pressed.
     */
    void enter(String entry) {
        String val = entry.trim()
        if (!val) { return }
        if (val in operators) {
            operate(val)
        } else {
            pushNumber(val)
        }
    }

    double peek() {
        stack.peek()
    }

    double pop() {
        stack.peek()
    }

    void clear() {
        stack.clear()
    }

    boolean isEmpty() {
        stack.isEmpty()
    }

    int size() {
        stack.size()
    }


    protected void operate(String entry) {
        switch (entry) {
            case '+':
                add()
                break
            case '-':
                minus()
                break
            case '/':
                divide()
                break
            case '*':
                multiply()
                break
        }
    }

    protected void pushNumber(String entry) {
        Double value = Double.valueOf(entry)
        stack.push(value)
    }

    void add() {
        double first = stack.pop()
        double second = stack.pop()
        stack.push(second + first)
    }

    void minus() {
        double first = stack.pop()
        double second = stack.pop()
        stack.push(second - first)
    }

    void divide() {
        double first = stack.pop()
        double second = stack.pop()
        stack.push(second / first)
    }

    void multiply() {
        double first = stack.pop()
        double second = stack.pop()
        stack.push(second * first)
    }

}
