package org.assignment;

public class CustomerResultThread extends Thread {

    private final MultiChannelSystem system;
    private CustomerResult result;

    public CustomerResultThread(MultiChannelSystem system) {
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
