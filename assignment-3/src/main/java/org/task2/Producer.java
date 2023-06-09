package org.task2;

import java.util.Random;

public class Producer implements Runnable {
    private final Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        int[] importantInfo = createValues(100);
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
            }
        }
        drop.put(-1);
    }

    private int[] createValues(int len) {
        int[] result = new int[len];
        for (int i = 0; i < len; ++ i) {
            result[i] = i;
        }
        return result;
    }
}
