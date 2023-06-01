package org.assignment;

public class CustomerResult {

    private int total;
    private int failed;
    private int served;
    private int averageTime;
    private double failureRate;

    public CustomerResult() {
        this.total = 0;
        this.failed = 0;
        this.served = 0;
        this.averageTime = 0;
        failureRate = 0;
    }

    public CustomerResult(int total, int failed, int served, int averageTime, double failureRate) {
        this.total = total;
        this.failed = failed;
        this.served = served;
        this.averageTime = averageTime;
        this.failureRate = failureRate;
    }
}
