package org.assignment.entity;

import org.assignment.service.MultiChannelService;

public class CustomerResultTask implements Runnable {

    private final MultiChannelService system;
    private CustomerResult result;

    public CustomerResultTask(MultiChannelService system) {
        this.system = system;
        this.result = new CustomerResult();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                result = system.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public CustomerResult getCustomerResult() {
        return result;
    }

}
