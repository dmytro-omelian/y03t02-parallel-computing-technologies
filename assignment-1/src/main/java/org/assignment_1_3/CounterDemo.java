package org.assignment_1_3;

public class CounterDemo {

    private static final int N_ITER = 10; // 100000;

    public static void main(String[] args) throws InterruptedException {
        SyncCounter counter = new SyncCounter();
//        AsyncCounter counter = new AsyncCounter();
//        LockCounter counter = new LockCounter();
//        SyncBlockCounter counter = new SyncBlockCounter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < N_ITER; i++) {
                counter.increment();
                counter.print();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < N_ITER; i++) {
                counter.decrement();
                counter.print();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        counter.print();
    }
}
