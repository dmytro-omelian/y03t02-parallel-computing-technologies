package org.assignment_1.entity;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RedBall extends Ball {
    public RedBall(Component c) {
        super(c);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

}
