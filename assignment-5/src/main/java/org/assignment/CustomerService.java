package org.assignment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class CustomerService implements Callable<Customer> {
    private final BlockingQueue<Customer> queue;
    private final Customer customer;

    public CustomerService(BlockingQueue<Customer> queue, Customer customer) {
        this.queue = queue;
        this.customer = customer;
    }

    @Override
    public Customer call() {
        try {
            Customer removedCustomer = queue.take();
            Thread.sleep(removedCustomer.serviceTime());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return customer;
    }
}
