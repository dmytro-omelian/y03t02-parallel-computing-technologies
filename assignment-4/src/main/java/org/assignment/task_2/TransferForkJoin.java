package org.assignment.task_2;

import java.util.concurrent.RecursiveAction;

public class TransferForkJoin extends RecursiveAction {
    private int reps;
    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;

    public TransferForkJoin(Bank b, int from, int max, int reps) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.reps = reps;
    }

    @Override
    protected void compute() {
        for (int i = 0; i < reps; i++) {
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random() / reps);
            bank.transferSync(fromAccount, toAccount, amount);
        }
    }
}
