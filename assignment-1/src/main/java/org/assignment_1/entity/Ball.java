package org.assignment_1.entity;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.Random;

class Ball extends BallForm {
    private int dx = 2;
    private int dy = 2;

    public Ball(Component c) {
        super(c);

        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public static void f() {
        int a = 0;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.darkGray);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
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

    public boolean isGotIntoWhole(List<Whole> wholes) {
        for (Whole whole : wholes) {
            if (isClose(whole)) {
                return true;
            }
        }
        return false;
    }

    private boolean isClose(Whole whole) {
        int manhDistance = Math.abs(whole.x - x) + Math.abs(whole.y - y);
        return manhDistance <= 10;
    }

}