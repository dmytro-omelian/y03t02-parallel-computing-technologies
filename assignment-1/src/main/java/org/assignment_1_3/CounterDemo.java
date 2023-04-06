package org.assignment_1_3;

public class CounterDemo {

    private static final int N_ITER = 10; // 100000;

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < N_ITER; i ++) {
                counter.increment();
                System.out.println(counter.getCounter());
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < N_ITER; i ++) {
                counter.decrement();
                System.out.println(counter.getCounter());
            }
        });
        thread1.start();
        thread2.start();
    }
}
