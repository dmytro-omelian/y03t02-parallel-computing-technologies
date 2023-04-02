package org.assignment_1.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BallsStorage {

    private final Map<Ball, BallThread> balls;
    private final List<Whole> wholes;

    public BallsStorage() {
        this.balls = new HashMap<>();
        this.wholes = new ArrayList<>();
    }

    public List<Ball> getBalls() {
        return this.balls.keySet().stream().toList();
    }

    public List<Whole> getWholes() {
        return wholes;
    }

    public void add(Ball ball, BallThread thread) {
        this.balls.put(ball, thread);
    }

    public void add(Whole b) {
        this.wholes.add(b);
    }

    public void remove(Ball ball) {
        Thread thread = this.balls.getOrDefault(ball, null);
        if (thread == null) {
            throw new RuntimeException("Thread is null...");
        }
        thread.stop();
        this.balls.remove(ball);
    }

}
