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
        for (Ball b : this.storage.getBalls()) {
            if (!b.isGotIntoWhole(this.storage.getWholes())) {
                b.draw(g2);
            } else {
                this.storage.remove(b);
            }
        }
        for (Whole w : this.storage.getWholes()) {
            w.draw(g2);
        }
    }
}