package org.assignment_1_3;

public class SyncCounter implements Counter {

    private int counter;

    public SyncCounter() {
        this.counter = 0;
    }

    @Override
    public synchronized void increment() {
        synchronized (this) {
            this.counter++;
        }
    }

    @Override
    public synchronized void decrement() {
        synchronized (this) {
            this.counter--;
        }
    }

    @Override
    public synchronized void print() {
        System.out.printf("Counter is %s\n", this.counter);
    }
}
