package org.assignment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerResult {

    private int total;
    private int failed;
    private int served;
    private int averageTime;
    private double failureRate;

    private ConcurrentHashMap<Integer, Integer> totalQueueLength;

    public CustomerResult() {
        this.total = 0;
        this.failed = 0;
        this.served = 0;
        this.averageTime = 0;
        failureRate = 0;
    }

    public CustomerResult(int total, int failed, int served, int averageTime, ConcurrentHashMap<Integer, Integer> totalQueueLength) {
        this.total = total;
        this.failed = failed;
        this.served = served;
        this.averageTime = averageTime;
        this.totalQueueLength = totalQueueLength;
        this.failureRate = (double) failed / total;
    }

    public int getTotal() {
        return total;
    }

    public int getFailed() {
        return failed;
    }

    public int getServed() {
        return served;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public double getFailureRate() {
        return failureRate;
    }

    @Override
    public String toString() {
        int sum = 0;
        int count = 0;
        for (Map.Entry<Integer, Integer> entry  : totalQueueLength.entrySet()) {
            sum += entry.getKey() * entry.getValue();
            count += entry.getValue();
        }
        String result = """
                Customers: %d
                Failed Customers: %d
                Served Customers: %d
                Average Service Time: %d
                Average Queue Length: %f
                Failure Probability: %.2f%%
                """.formatted(total, failed, served, averageTime, (double) sum/count, failureRate*100);
        return result;
    }

    public ConcurrentHashMap<Integer, Integer> getTotalQueueLength() {
        return totalQueueLength;
    }
}
