package org.assignment_1.entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private final BallCanvas canvas;
    protected final BallsStorage storage;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.storage = new BallsStorage();
        this.canvas = new BallCanvas(storage);
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        Whole whole = new Whole(canvas);
        this.storage.add(whole);

        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runBall();
            }

            void runBall() {
                Ball b = new Ball(canvas);
                BallThread thread = new BallThread(b);

                thread.start();
                storage.add(b, thread);

                System.out.println("Thread name = " +
                        thread.getName());
            }

        });
        buttonStop.addActionListener(e -> System.exit(0));
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}