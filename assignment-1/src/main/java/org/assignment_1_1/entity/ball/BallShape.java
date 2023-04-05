package org.assignment_1_1.entity.ball;

import java.awt.*;

public abstract class BallShape {
    protected static final int XSIZE = 20;
    protected static final int YSIZE = 20;
    protected final Component canvas;
    protected int x = 0;
    protected int y = 0;
    protected Color color;

    public BallShape(Component c, Color color) {
        this.canvas = c;
        this.color = color;
    }

    public abstract void draw(Graphics2D g2);

}
