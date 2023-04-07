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

    public synchronized void waitAndChange(boolean controlValue, char symbol) {
        while (getPermission() != controlValue) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("error occurred...");
            }
        }
        System.out.print(symbol);
        step();
        notifyAll();
    }

    public void step() {
        this.permission = !this.permission;
        this.num++;
        if (this.num % 100 == 0) System.out.println();

        if (this.num == 100000) this.stop = true;
    }

}
