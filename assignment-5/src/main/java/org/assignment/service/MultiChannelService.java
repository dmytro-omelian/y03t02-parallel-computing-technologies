package org.assignment.service;

import org.assignment.config.Config;
import org.assignment.entity.Customer;
import org.assignment.entity.CustomerQueueObserver;
import org.assignment.entity.CustomerServiceTask;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiChannelService extends Thread {
    private final AtomicInteger failed = new AtomicInteger(0);
    private final AtomicInteger served = new AtomicInteger(0);
    private final AtomicInteger totalTime = new AtomicInteger(0);

    private final ConcurrentHashMap<Integer, Integer> totalQueueLength;
    private final BlockingQueue<Customer> queue;

    public MultiChannelService() {
        this.totalQueueLength = new ConcurrentHashMap<>();
        this.queue = new ArrayBlockingQueue<>(Config.QUEUE_SIZE);
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(Config.N_CHANNELS);
        try {
            int i = 0;
            while (true) {
                Customer customer = new Customer(i + 1, getRandomNumberInRange(Config.MIN_TIME_SERVICE, Config.MAX_TIME_SERVICE));

                if (queue.size() == Config.QUEUE_SIZE) {
                    failed.incrementAndGet();
                } else {
                    queue.put(customer);
                    executorService.submit(new CustomerServiceTask(queue, customer));
                    served.incrementAndGet();
                    totalTime.addAndGet(customer.serviceTime());
                }

                Thread.sleep(getRandomNumberInRange(Config.MIN_TIME_ARRIVE, Config.MAX_TIME_ARRIVE));
                i++;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private int getRandomNumberInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public CustomerQueueObserver getResult() {
        totalQueueLength.put(queue.size(), totalQueueLength.getOrDefault(queue.size(), 0) + 1);
        int totalCustomers = served.get() + failed.get();
        int failedCustomers = failed.get();
        int servedCustomers = served.get();
        int averageServiceTime = served.get() != 0 ? totalTime.get() / served.get() : 0;
        return new CustomerQueueObserver(totalCustomers, failedCustomers, servedCustomers, averageServiceTime, totalQueueLength);
    }
}
