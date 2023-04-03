package org.assignment_1.entity;

public class BallThread extends Thread {
    private final GreyBall b;

    public BallThread(GreyBall greyBall) {
        b = greyBall;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {

        }
    }
}