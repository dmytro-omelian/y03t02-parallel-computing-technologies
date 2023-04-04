package org.assignment_1.entity.ball;

import java.awt.*;
import java.util.Random;

class GreyBall extends MovableBall {

    public GreyBall(Component c) {
        super(c);

        if (Math.random() < 0.5) {
            this.x = new Random().nextInt(this.canvas.getWidth());
            this.y = 0;
        } else {
            this.x = 0;
            this.y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public GreyBall(Component c, int x, int y) {
        super(c);

        this.x = x;
        this.y = y;
    }

}