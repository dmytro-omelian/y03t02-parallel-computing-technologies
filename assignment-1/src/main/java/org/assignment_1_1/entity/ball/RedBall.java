package org.assignment_1_1.entity.ball;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class RedBall extends MovableBall {
    public RedBall(Component c) {
        super(c);

        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

}
