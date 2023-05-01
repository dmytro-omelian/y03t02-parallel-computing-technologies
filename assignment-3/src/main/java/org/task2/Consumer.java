package org.task2;

import java.util.Random;

public class Consumer implements Runnable {
    private final Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (Integer value = drop.take(); value != -1; value = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %d%n", value);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
            }
        }
    }
}
