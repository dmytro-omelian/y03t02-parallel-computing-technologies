package org.assignment.entity;

import org.assignment.config.Config;
import org.assignment.service.MultiChannelService;

public class CustomerResultTask implements Runnable {

    private final MultiChannelService system;
    private CustomerQueueObserver result;
    private int totalChecks = 0;

    public CustomerResultTask(MultiChannelService system) {
        this.system = system;
        this.result = new CustomerQueueObserver();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                result = system.getResult();
                totalChecks++;
                if (totalChecks % Config.CHECKS_PER_OUTPUT == 0) {
                    this.printSystemOutputParameters();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void printSystemOutputParameters() {
        var averageQueueSize = this.result.getAverageQueueSize();
        var failureProbability = this.result.getFailureProbability();
        System.out.println("System #" + system.getId() +
                " Average queue size: " + averageQueueSize +
                " | Failure probability: " + failureProbability
        );
    }

    public CustomerQueueObserver getCustomerResult() {
        return result;
    }

}
