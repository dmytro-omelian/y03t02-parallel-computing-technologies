package org.task2;

public class Drop {
    private Integer value;
    private boolean empty = true;

    public synchronized Integer take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        empty = true;
        notifyAll();
        return value;
    }

    public synchronized void put(int value) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        empty = false;
        this.value = value;
        notifyAll();
    }
}
