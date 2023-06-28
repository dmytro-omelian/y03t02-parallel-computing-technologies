package org.assignment.entity;

import java.util.concurrent.ConcurrentHashMap;

public class CustomerQueueObserver {

    private int total;
    private int failed;
    private int served;
    private int averageTime;

    private ConcurrentHashMap<Integer, Integer> totalQueueLength;

    public CustomerQueueObserver() {
        this.total = 0;
        this.failed = 0;
        this.served = 0;
        this.averageTime = 0;
        this.totalQueueLength = new ConcurrentHashMap<>();
    }

    public CustomerQueueObserver(int total, int failed, int served, int averageTime, ConcurrentHashMap<Integer, Integer> totalQueueLength) {
        this.total = total;
        this.failed = failed;
        this.served = served;
        this.averageTime = averageTime;
        this.totalQueueLength = totalQueueLength;
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

    public void update(CustomerQueueObserver result) {
        this.total += result.getTotal();
        this.failed += result.getFailed();
        this.served += result.getServed();
        this.averageTime += result.getAverageTime();

        for (Integer queueLength : result.getTotalQueueLength().keySet()) {
            totalQueueLength.merge(queueLength, result.getTotalQueueLength().get(queueLength), Integer::sum);
        }
    }

    public void normalize(int systems) {
        this.total /= systems;
        this.failed /= systems;
        this.served /= systems;
        this.averageTime /= systems;

        totalQueueLength.replaceAll((l, v) -> v / systems);
    }

    @Override
    public String toString() {
        double avrQueueLen = this.getAverageQueueSize();
        double failureRate = this.getFailureProbability();

        return """
                Customers: %d
                Failed: %d
                Served: %d
                Average service time: %d
                Average queue length: %f
                Failure probability: %.1f%%
                """.formatted(total, failed, served, averageTime, avrQueueLen, failureRate * 100);
    }

    public ConcurrentHashMap<Integer, Integer> getTotalQueueLength() {
        return totalQueueLength;
    }

    public double getAverageQueueSize() {
        int sum = 0;
        int count = 0;
        for (var entry : totalQueueLength.entrySet()) {
            sum += entry.getKey() * entry.getValue();
            count += entry.getValue();
        }
        return count != 0 ? (double) sum / count : 0;
    }

    public double getFailureProbability() {
        return (double) failed / total;
    }
}
