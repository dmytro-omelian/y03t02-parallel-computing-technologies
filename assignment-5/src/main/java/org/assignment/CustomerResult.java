package org.assignment;

import java.util.concurrent.ConcurrentHashMap;

public class CustomerResult {

    private final int total;
    private final int failed;
    private final int served;
    private final int averageTime;
    private final double failureRate;

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

    @Override
    public String toString() {
        int sum = 0;
        int count = 0;
        for (var entry : totalQueueLength.entrySet()) {
            sum += entry.getKey() * entry.getValue();
            count += entry.getValue();
        }
        return """
                Customers: %d
                Failed: %d
                Served: %d
                Average service time: %d
                Average queue length: %f
                Failure probability: %.1f%%
                """.formatted(total, failed, served, averageTime, (double) sum/count, failureRate*100);
    }

    public ConcurrentHashMap<Integer, Integer> getTotalQueueLength() {
        return totalQueueLength;
    }
}
