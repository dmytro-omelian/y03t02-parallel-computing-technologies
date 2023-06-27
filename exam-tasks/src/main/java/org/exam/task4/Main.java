package org.exam.task4;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static final AtomicBoolean state = new AtomicBoolean(true);

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();

        Thread mThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    state.set(!state.get());
                    System.out.println("State: " + state.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread nThread = new Thread(() -> {
            while (true) {
                if (state.get()) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Time taken: " + (endTime - startTime) + "ms");
                } else {
                    System.out.println("nThread completed.");
                    return;
                }
            }
        });

        mThread.start();
        nThread.start();

        try {
            mThread.join();
            nThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
