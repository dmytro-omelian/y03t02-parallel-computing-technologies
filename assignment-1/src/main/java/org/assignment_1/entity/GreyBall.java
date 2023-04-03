package org.assignment_1.entity;

import java.awt.*;
import java.util.Random;

class GreyBall extends MovableBall {

    public GreyBall(Component c) {
        super(c);

        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

}