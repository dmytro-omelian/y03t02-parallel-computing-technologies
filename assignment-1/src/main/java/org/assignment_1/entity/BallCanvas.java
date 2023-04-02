package org.assignment_1.entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallCanvas extends JPanel {
    private final List<Ball> balls = new ArrayList<>();
    private final List<Whole> wholes = new ArrayList<>();

    public void add(BallForm b) {
        if (b instanceof Ball) {
            this.balls.add((Ball) b);
        } else if (b instanceof  Whole) {
            this.wholes.add((Whole) b);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            b.draw(g2);
        }
        for (Whole w : wholes) {
            w.draw(g2);
        }
    }
}