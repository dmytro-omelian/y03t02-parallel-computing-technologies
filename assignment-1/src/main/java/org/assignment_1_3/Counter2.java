package org.assignment_1_3;

public class Counter2 {

    private int counter;

    public Counter2() {
        this.counter = 0;
    }

    public void increment() {
        synchronized (this) {
            this.counter++;
            System.out.println(this.counter);
        }
    }

    public void decrement() {
        synchronized (this) {
            this.counter--;
            System.out.println(this.counter);
        }
    }

    public synchronized int getCount() {
        return this.counter;
    }

}
