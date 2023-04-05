package org.assignment_1_2;

public class Sync {

    private boolean permission;
    private int num;
    private boolean stop;

    public Sync() {
        this.permission = true;
        this.num = 0;
        this.stop = false;
    }

    public synchronized boolean getPermission() {
        return this.permission;
    }

    public synchronized boolean isStop() {
        return this.stop;
    }

    public synchronized void changePermission() {
        permission = !permission;
        num++;
        if (num % 100 == 0) System.out.println();

        if (num == 100000) stop = true;
    }

    public synchronized void waitAndChange(boolean controlValue, char symbol) {
        while (getPermission() != controlValue) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("error occurred...");
            }
        }
        System.out.print(symbol);
        permission = !permission;
        num++;
        if (num % 100 == 0) System.out.println();

        if (num == 100000) stop = true;
        notifyAll();
    }

}
