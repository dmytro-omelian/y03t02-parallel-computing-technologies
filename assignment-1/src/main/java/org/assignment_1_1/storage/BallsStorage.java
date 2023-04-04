package org.assignment_1_1.storage;

import org.assignment_1_1.entity.ball.MovableBall;
import org.assignment_1_1.entity.ball.Whole;
import org.assignment_1_1.entity.thread.BallThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BallsStorage {

    private final Map<MovableBall, BallThread> balls;
    private final List<Whole> wholes;

    public BallsStorage() {
        this.balls = new HashMap<>();
        this.wholes = new ArrayList<>();
    }

    public List<? extends MovableBall> getBalls() {
        return this.balls.keySet().stream().toList();
    }

    public List<Whole> getWholes() {
        return wholes;
    }

    public void add(MovableBall greyBall, BallThread thread) {
        this.balls.put(greyBall, thread);
    }

    public void add(Whole b) {
        this.wholes.add(b);
    }

    public void remove(MovableBall greyBall) {
        Thread thread = this.balls.getOrDefault(greyBall, null);
        if (thread == null) {
            throw new RuntimeException("Thread is null...");
        }
        thread.stop();
        this.balls.remove(greyBall);
    }

}
