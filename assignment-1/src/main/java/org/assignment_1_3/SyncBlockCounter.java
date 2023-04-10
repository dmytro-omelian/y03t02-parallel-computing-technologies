package org.assignment_1_3;

public class SyncBlockCounter implements Counter {

    private int counter;

    public SyncBlockCounter() {
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
    public void print() {
        System.out.printf("Counter is %s\n", counter);
    }
}
