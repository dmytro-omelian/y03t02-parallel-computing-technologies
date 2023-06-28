package org.assignment;

import org.assignment.entity.CustomerQueueObserver;
import org.assignment.entity.CustomerResultTask;
import org.assignment.service.MultiChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int SYSTEMS = 5;

    public static void main(String[] args) {
//        serialSimulationSingle();
        parallelSimulationMultiple();
    }

    private static void parallelSimulationMultiple() {
        ExecutorService systemService = Executors.newFixedThreadPool(SYSTEMS);
        List<CustomerResultTask> resultTasks = new ArrayList<>();
        for (int i = 0; i < SYSTEMS; i++) {
            MultiChannelService system = new MultiChannelService();
            systemService.submit(system);
            CustomerResultTask resultTask = new CustomerResultTask(system);
            resultTasks.add(resultTask);
        }
        systemService.shutdown();

        ExecutorService resultTaskService = Executors.newFixedThreadPool(SYSTEMS);
        for (CustomerResultTask resultTask: resultTasks) {
            resultTaskService.submit(resultTask);
        }
        resultTaskService.shutdown();

        while (true) {
            try {
                Thread.sleep(5000);
                CustomerQueueObserver averageResult = getAverageResult(resultTasks);
                System.out.println("\nAverage result for " + SYSTEMS + " systems:");
                System.out.println(averageResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static void serialSimulationSingle() {
        MultiChannelService system = new MultiChannelService();
        system.start();

        CustomerResultTask resultTask = new CustomerResultTask(system);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(resultTask);
        executorService.shutdown();

        while (true) {
            try {
                Thread.sleep(5000);
                CustomerQueueObserver averageResult = getAverageResult(List.of(resultTask));
                System.out.println("Average result for 1 system:");
                System.out.println(averageResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static CustomerQueueObserver getAverageResult(List<CustomerResultTask> results) {
        CustomerQueueObserver result = new CustomerQueueObserver();
        for (CustomerResultTask resultTask : results) {
            CustomerQueueObserver tempResult = resultTask.getCustomerResult();
            result.update(tempResult);
        }
        result.normalize(SYSTEMS);
        return result;
    }
}
