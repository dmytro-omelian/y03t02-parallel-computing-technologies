package org.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private static final int SYSTEMS = 5;

    public static void main(String[] args) {
        List<CustomerResultThread> resultThreads = new ArrayList<>();
        for (int i = 0; i < SYSTEMS; i++) {
            MultiChannelSystem system = new MultiChannelSystem();
            system.start();
            CustomerResultThread resultThread = new CustomerResultThread(system);
            resultThreads.add(resultThread);
            resultThread.start();
        }

        while (true) {
            try {
                Thread.sleep(5000);
                CustomerResult averageResult = getAverageResult(resultThreads);
                System.out.println("Average result for " + SYSTEMS + " systems:");
                System.out.println(averageResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
    public static CustomerResult getAverageResult(List<CustomerResultThread> results){
        int totalCustomers = 0;
        int totalFailedCustomers = 0;
        int totalServedCustomers = 0;
        int averageServiceTime = 0;
        ConcurrentHashMap<Integer, Integer> totalQueueLength = new ConcurrentHashMap<>();
        for (CustomerResultThread resultThread : results) {
            CustomerResult result = resultThread.getCustomerResult();
            totalCustomers += result.getTotal();
            totalFailedCustomers += result.getFailed();
            totalServedCustomers += result.getServed();
            averageServiceTime += result.getAverageTime();
            for (Integer queueLength : result.getTotalQueueLength().keySet()) {
                totalQueueLength.put(queueLength, totalQueueLength.getOrDefault(queueLength, 0) + result.getTotalQueueLength().get(queueLength));
            }
        }
        totalCustomers /= SYSTEMS;
        totalFailedCustomers /= SYSTEMS;
        totalServedCustomers /= SYSTEMS;
        averageServiceTime /= SYSTEMS;
        totalQueueLength.replaceAll((l, v) -> totalQueueLength.get(l) / SYSTEMS);
        return new CustomerResult(totalCustomers, totalFailedCustomers, totalServedCustomers, averageServiceTime, totalQueueLength);
    }
}