package org.assignment_1.entity;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Whole extends BallForm {

    private Integer counter;

    public Whole(Component c) {
        super(c);

        this.counter = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
        g2.setColor(Color.WHITE);
        g2.drawString(Integer.toString(counter), x + XSIZE / 2, y + YSIZE / 2);
    }

    public synchronized void increaseCounter() {
        this.counter++;
    }
}
