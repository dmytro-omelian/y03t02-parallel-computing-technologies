package org.assignment_1.entity;

public class BallThread extends Thread {
    private final MovableBall ball;

    public BallThread(MovableBall ball) {
        this.ball = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                ball.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {

        }
    }
}