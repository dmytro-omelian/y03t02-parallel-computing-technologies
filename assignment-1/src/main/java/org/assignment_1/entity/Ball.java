package org.assignment_1.entity;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball {
    protected static final int XSIZE = 20;
    protected static final int YSIZE = 20;
    protected final Component canvas;
    protected int x = 0;
    protected int y = 0;

    public Ball(Component c) {
        this.canvas = c;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.darkGray);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

}
