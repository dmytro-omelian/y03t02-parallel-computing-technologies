package org.assignment_1.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BallsStorage {

    private final Map<GreyBall, BallThread> balls;
    private final List<Whole> wholes;

    public BallsStorage() {
        this.balls = new HashMap<>();
        this.wholes = new ArrayList<>();
    }

    public List<GreyBall> getBalls() {
        return this.balls.keySet().stream().toList();
    }

    public List<Whole> getWholes() {
        return wholes;
    }

    public void add(GreyBall greyBall, BallThread thread) {
        this.balls.put(greyBall, thread);
    }

    public void add(Whole b) {
        this.wholes.add(b);
    }

    public void remove(GreyBall greyBall) {
        Thread thread = this.balls.getOrDefault(greyBall, null);
        if (thread == null) {
            throw new RuntimeException("Thread is null...");
        }
        thread.stop();
        this.balls.remove(greyBall);
    }

}
