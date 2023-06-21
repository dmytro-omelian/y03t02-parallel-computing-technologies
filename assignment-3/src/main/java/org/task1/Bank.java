package org.task1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    public synchronized void transferSync(int from, int to, int amount) {
        while (accounts[from] < amount) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        notifyAll();
        if (ntransacts % NTEST == 0)
            test();
    }

    public void transferSyncBlock(int from, int to, int amount) {
        synchronized (this) {
            while (accounts[from] < amount) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            notifyAll();
            if (ntransacts % NTEST == 0)
                test();
        }
    }

    public void transferLocker(int from, int to, int amount) {
        lock.lock();
        try {
            while (accounts[from] < amount) {
                notEmpty.await();
            }
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            notEmpty.signalAll();
            if (ntransacts % NTEST == 0)
                test();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void test() {
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i];
        System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }

    public void transferAsync(int fromAccount, int toAccount, int amount) {
        accounts[fromAccount] -= amount;
        accounts[toAccount] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }
}