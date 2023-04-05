package org.assignment_1_1.entity.thread;

import org.assignment_1_1.entity.ball.Ball;

public class BallThread extends Thread {
    private final Ball ball;
    private BallThread parentThread;

    public BallThread(Ball ball) {
        this.ball = ball;
    }

    public BallThread(Ball ball, BallThread parentThread) {
        this.ball = ball;
        this.parentThread = parentThread;
    }

    @Override
    public void run() {
        try {
            if (parentThread != null) {
                parentThread.join();
            }
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