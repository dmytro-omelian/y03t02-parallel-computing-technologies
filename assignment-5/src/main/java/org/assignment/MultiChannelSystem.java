package org.assignment;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiChannelSystem extends Thread {
    private final AtomicInteger totalFailedCustomers = new AtomicInteger(0);
    private final AtomicInteger totalServedCustomers = new AtomicInteger(0);
    private final AtomicInteger totalServiceTime = new AtomicInteger(0);

    private final ConcurrentHashMap<Integer, Integer> totalQueueLength;
    private final BlockingQueue<Customer> queue;

    public MultiChannelSystem() {
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
                    totalFailedCustomers.incrementAndGet();
                } else {
                    queue.put(customer);
                    executorService.submit(new CustomerService(queue, customer));
                    totalServedCustomers.incrementAndGet();
                    totalServiceTime.addAndGet(customer.serviceTime());
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

    public CustomerResult getResult() {
        totalQueueLength.put(queue.size(), totalQueueLength.getOrDefault(queue.size(), 0) + 1);
        int totalCustomers = totalServedCustomers.get() + totalFailedCustomers.get();
        int failedCustomers = totalFailedCustomers.get();
        int servedCustomers = totalServedCustomers.get();
        int averageServiceTime = totalServedCustomers.get() != 0 ? totalServiceTime.get() / totalServedCustomers.get() : 0;
        return new CustomerResult(totalCustomers, failedCustomers, servedCustomers, averageServiceTime, totalQueueLength);
    }
}
