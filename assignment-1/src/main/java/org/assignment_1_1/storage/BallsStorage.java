package org.assignment_1_1.storage;

import org.assignment_1_1.entity.ball.Ball;
import org.assignment_1_1.entity.ball.Whole;
import org.assignment_1_1.entity.thread.BallThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BallsStorage {

    private final Map<Ball, BallThread> balls;
    private final List<Whole> wholes;
    private Ball lastBall;

    public BallsStorage() {
        this.balls = new HashMap<>();
        this.wholes = new ArrayList<>();
    }

    public List<? extends Ball> getBalls() {
        return this.balls.keySet().stream().toList();
    }

    public List<Whole> getWholes() {
        return wholes;
    }

    public void add(Ball greyBall, BallThread thread) {
        this.balls.put(greyBall, thread);
        this.lastBall = greyBall;
    }

    public void add(Whole b) {
        this.wholes.add(b);
    }

    public void remove(Ball greyBall) {
        Thread thread = this.balls.getOrDefault(greyBall, null);
        if (thread == null) {
            throw new RuntimeException("Thread is null...");
        }
        thread.interrupt(); // how to move to terminated?
        this.balls.remove(greyBall);
    }

    public Ball getLastBall() {
        return this.lastBall;
    }

    public BallThread getThreadByBall(Ball lastBall) {
        return this.balls.getOrDefault(lastBall, null);
    }
}
