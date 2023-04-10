package org.assignment_1_3;

public class AsyncCounter implements Counter {

    private int counter;

    public AsyncCounter() {
        this.counter = 0;
    }


    @Override
    public void increment() {
        this.counter++;
    }

    @Override
    public void decrement() {
        this.counter--;
    }

    @Override
    public void print() {
        System.out.printf("Counter is %s\n", counter);
    }
}
