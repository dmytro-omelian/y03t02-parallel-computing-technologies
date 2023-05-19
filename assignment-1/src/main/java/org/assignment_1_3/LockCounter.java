package org.assignment_1_3;

import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements Counter {

    private final ReentrantLock lock;
    private int counter;

    public LockCounter() {
        this.counter = 0;
        this.lock = new ReentrantLock();
    }

    @Override
    public void increment() {
        lock.lock();
        try {
            this.counter++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void decrement() {
        lock.lock();
        try {
            this.counter--;
        } finally {
            lock.unlock();
        }
    }
}
