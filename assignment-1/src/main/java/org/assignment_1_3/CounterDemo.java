package org.assignment_1_3;

public class CounterDemo {

    private static final int N_ITER = 10; // 100000;

    public static void main(String[] args) throws InterruptedException {
        Counter1 counter = new Counter1();
//        Counter2 counter = new Counter2();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < N_ITER; i ++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < N_ITER; i ++) {
                counter.decrement();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + counter.getCount());
    }
}
