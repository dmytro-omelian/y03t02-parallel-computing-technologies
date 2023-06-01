package org.assignment;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiChannelSystem extends Thread {
    public static int idCounter = 0;
    private int id;
    private static final int SERVICE_CHANNELS = 8;
    private static final int QUEUE_SIZE = 10;
    private static final int SERVICE_TIME_MIN = 500;
    private static final int SERVICE_TIME_MAX = 1500;
    private static final int ARRIVAL_TIME_MIN = 50;
    private static final int ARRIVAL_TIME_MAX = 150;
    private final AtomicInteger totalFailedCustomers = new AtomicInteger(0);
    private final AtomicInteger totalServedCustomers = new AtomicInteger(0);
    private final AtomicInteger totalServiceTime = new AtomicInteger(0);
    private final ConcurrentHashMap<Integer, Integer> totalQueueLength = new ConcurrentHashMap<>();
    BlockingQueue<Customer> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
    public MultiChannelSystem(){
        this.id = ++idCounter;
    }
    @Override
    public void run() {

        ExecutorService executorService = Executors.newFixedThreadPool(SERVICE_CHANNELS);
        try {

            int i = 0;
            while (true){
                Customer customer = new Customer(i + 1, getRandomNumberInRange(SERVICE_TIME_MIN, SERVICE_TIME_MAX));

                if (queue.size() == QUEUE_SIZE) {
                    totalFailedCustomers.incrementAndGet();
                } else {
                    queue.put(customer);
                    executorService.submit(new CustomerService(queue, customer));
                    totalServedCustomers.incrementAndGet();
                    totalServiceTime.addAndGet(customer.serviceTime());
                }

                Thread.sleep(getRandomNumberInRange(ARRIVAL_TIME_MIN, ARRIVAL_TIME_MAX));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    private int getRandomNumberInRange(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    public CustomerResult getResult() {
        totalQueueLength.put(queue.size(), totalQueueLength.getOrDefault(queue.size(), 0) + 1);
        return new CustomerResult(totalServedCustomers.get() + totalFailedCustomers.get(), totalFailedCustomers.get(), totalServedCustomers.get(), totalServiceTime.get() / totalServedCustomers.get(), totalQueueLength);
    }

    public int getSystemId() {
        return id;
    }
}
