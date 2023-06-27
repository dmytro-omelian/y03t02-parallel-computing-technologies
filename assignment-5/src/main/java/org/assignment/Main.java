package org.assignment;

import org.assignment.entity.CustomerResult;
import org.assignment.entity.CustomerResultTask;
import org.assignment.service.MultiChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int SYSTEMS = 5;

    public static void main(String[] args) {

        ExecutorService systemService = Executors.newFixedThreadPool(SYSTEMS);
        List<CustomerResultTask> resultThreads = new ArrayList<>();
        for (int i = 0; i < SYSTEMS; i++) {
            MultiChannelService system = new MultiChannelService();
            systemService.submit(system);
            CustomerResultTask resultTask = new CustomerResultTask(system);
            resultThreads.add(resultTask);
        }
        systemService.shutdown();

        ExecutorService resultTaskService = Executors.newFixedThreadPool(SYSTEMS);
        for (CustomerResultTask resultTask: resultThreads) {
            resultTaskService.submit(resultTask);
        }
        resultTaskService.shutdown();

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

    public static CustomerResult getAverageResult(List<CustomerResultTask> results) {
        CustomerResult result = new CustomerResult();
        for (CustomerResultTask resultTask : results) {
            CustomerResult tempResult = resultTask.getCustomerResult();
            result.update(tempResult);
        }
        result.normalize(SYSTEMS);
        return result;
    }
}
