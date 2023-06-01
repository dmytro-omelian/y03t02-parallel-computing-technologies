package org.assignment.task_2;

class TransferThread extends Thread {
    private int reps;
    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;

    public TransferThread(Bank b, int from, int max, int reps) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.reps = reps;
    }

    @Override
    public void run() {
        for (int i = 0; i < reps; i++) {
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random() / reps);
            bank.transferSync(fromAccount, toAccount, amount);
        }
    }
}
