package org.assignment_1_3;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private int counter;

    public Counter() {
        this.counter = 0;
    }

    public void increment() {
        this.counter++;
    }

    public void decrement() {
        this.counter--;
    }

    public int getCounter() {
        return this.counter;
    }

}
