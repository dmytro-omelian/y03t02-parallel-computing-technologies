package org.assignment_1.entity.ball;

import java.awt.*;
import java.util.List;

public class MovableBall extends Ball {
    private int dx = 2;
    private int dy = 2;

    public MovableBall(Component c) {
        super(c);
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

}
