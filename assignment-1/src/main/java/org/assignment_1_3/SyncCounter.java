package org.assignment_1_3;

public class SyncCounter implements Counter {

    private int counter;

    public SyncCounter() {
        this.counter = 0;
    }

    @Override
    public synchronized void increment() {
        counter++;
//        synchronized (this) {
//            this.counter++;
//        }
    }

    @Override
    public synchronized void decrement() {
        counter--;
//        synchronized (this) {
//            this.counter--;
//        }
    }

    public int getCounter() {
        return this.counter;
    }

}
