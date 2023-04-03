package org.assignment_1.entity;

import javax.swing.*;
import java.awt.*;

public class BallCanvas extends JPanel {

    private final BallsStorage storage;

    public BallCanvas(BallsStorage storage) {
        this.storage = storage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (MovableBall b : this.storage.getBalls()) {
            Whole w = b.isGotIntoWhole(this.storage.getWholes());
            if (w == null) {
                b.draw(g2);
            } else {
                w.increaseCounter();
                this.storage.remove(b);
            }
        }
        for (Whole w : this.storage.getWholes()) {
            w.draw(g2);
        }
    }
}