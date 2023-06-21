package org.assignment.task_2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

public class SynchBankTest {
    public static final int REPS = 2_000_000;
    public static final int NACCOUNTS = 100;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        testSynhThread();
        testForkJoin();
    }

    private static void testSynhThread() {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        List<TransferThread> threads = new ArrayList<>();
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThread t = new TransferThread(b, i, INITIAL_BALANCE, REPS);
            threads.add(t);
        }

        try {
            long start = System.currentTimeMillis();
            for (var thread: threads) {
                thread.start();
            }
            for (var thread: threads) {
                thread.join();
            }
            long end = System.currentTimeMillis();

            System.out.println("Threads worked for: " + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void testForkJoin() {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        List<TransferForkJoin> threads = new ArrayList<>();
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferForkJoin t = new TransferForkJoin(b, i, INITIAL_BALANCE, REPS);
            threads.add(t);
        }

        try {
            long start = System.currentTimeMillis();
            ForkJoinTask.invokeAll(threads);
            long end = System.currentTimeMillis();

            System.out.println("ForkJoin worked for: " + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
