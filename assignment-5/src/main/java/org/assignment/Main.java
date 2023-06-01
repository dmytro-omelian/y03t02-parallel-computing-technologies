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

    public static CustomerResult getAverageResult(List<CustomerResultThread> results) {
        int totalCustomers = 0;
        int totalFailedCustomers = 0;
        int totalServedCustomers = 0;
        int totalServiceTime = 0;
        ConcurrentHashMap<Integer, Integer> totalQueueLength = new ConcurrentHashMap<>();

        for (CustomerResultThread resultThread : results) {
            CustomerResult result = resultThread.getCustomerResult();
            totalCustomers += result.getTotal();
            totalFailedCustomers += result.getFailed();
            totalServedCustomers += result.getServed();
            totalServiceTime += result.getAverageTime();

            for (Integer queueLength : result.getTotalQueueLength().keySet()) {
                totalQueueLength.merge(queueLength, result.getTotalQueueLength().get(queueLength), Integer::sum);
            }
        }

        totalCustomers /= SYSTEMS;
        totalFailedCustomers /= SYSTEMS;
        totalServedCustomers /= SYSTEMS;
        totalServiceTime /= SYSTEMS;

        totalQueueLength.replaceAll((l, v) -> v / SYSTEMS);

        return new CustomerResult(totalCustomers, totalFailedCustomers, totalServedCustomers, totalServiceTime, totalQueueLength);
    }
}
