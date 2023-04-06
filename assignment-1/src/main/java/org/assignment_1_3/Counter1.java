package org.assignment_1_3;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter1 {

    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        System.out.println(this.counter.addAndGet(1));
    }

    public void decrement() {
        System.out.println(this.counter.decrementAndGet());
    }

    public int getCount() {
        return this.counter.get();
    }

}
