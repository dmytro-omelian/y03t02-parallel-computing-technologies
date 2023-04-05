package org.assignment_1_1.entity.ball;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;

public class Ball extends BallShape {
    private int dx = 2;
    private int dy = 2;

    public Ball(Component c, Color color) {
        super(c, color);

        if (Math.random() < 0.5) {
            this.x = new Random().nextInt(this.canvas.getWidth());
            this.y = 0;
        } else {
            this.x = 0;
            this.y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public Ball(Component c, int x, int y, Color color) {
        super(c, color);

        this.x = x;
        this.y = y;
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }

    public Whole isGotIntoWhole(List<Whole> wholes) {
        for (Whole whole : wholes) {
            if (isClose(whole)) {
                return whole;
            }
        }
        return null;
    }

    private boolean isClose(Whole whole) {
        int manhDistance = Math.abs(whole.x - x) + Math.abs(whole.y - y);
        return manhDistance <= 10;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(this.color);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

}
