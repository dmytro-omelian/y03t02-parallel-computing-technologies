package org.assignment_1.entity.frame;

import org.assignment_1.canvas.BallCanvas;
import org.assignment_1.entity.GreyBall;
import org.assignment_1.entity.ball.RedBall;
import org.assignment_1.entity.ball.Whole;
import org.assignment_1.entity.thread.BallThread;
import org.assignment_1.storage.BallsStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BounceFrame extends JFrame {
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    protected final BallsStorage storage;
    private final BallCanvas canvas;

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

        startButton(buttonPanel);
        stopButton(buttonPanel);
        redBallButton(buttonPanel);
        experimentLotsBallsButton(buttonPanel);
//        joinBallButton(buttonPanel);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void joinBallButton(JPanel buttonPanel) {
        JButton buttonStart = new JButton("Join Ball");
        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    runBall();
                } catch (InterruptedException ex) {
                    System.err.println("Error occurred: " + ex);
                }
            }

            void runBall() throws InterruptedException {
                GreyBall b = new GreyBall(canvas);
                BallThread thread = new BallThread(b);
                thread.setPriority(1);
                storage.add(b, thread);
                thread.join();

                thread.start();

                System.out.println("Thread name = " +
                        thread.getName());
            }
        });
        buttonPanel.add(buttonStart);
    }

    private void experimentLotsBallsButton(JPanel buttonPanel) {
        JButton buttonRed = new JButton("1000 balls exp.");
        buttonRed.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x = 0;
                int y = new Random().nextInt(canvas.getWidth());
                for (int i = 0; i < 1000; ++i) {
                    runBall(x, y);
                }
            }

            void runBall(int x, int y) {
                GreyBall r = new GreyBall(canvas, x, y);
                BallThread thread = new BallThread(r);
                thread.setPriority(1);
                storage.add(r, thread);
                thread.start();

                System.out.println("Thread name = " +
                        thread.getName());
            }
        });
        buttonPanel.add(buttonRed);
    }

    private void redBallButton(JPanel buttonPanel) {
        JButton buttonRed = new JButton("Red ball start");
        buttonRed.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runBall();
            }

            void runBall() {
                RedBall r = new RedBall(canvas);
                BallThread thread = new BallThread(r);
                thread.setPriority(10);
                thread.start();
                storage.add(r, thread);

                System.out.println("Thread name = " +
                        thread.getName());
            }

        });
        buttonPanel.add(buttonRed);
    }

    private void startButton(JPanel buttonPanel) {
        JButton buttonStart = new JButton("Start");
        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runBall();
            }

            void runBall() {
                GreyBall b = new GreyBall(canvas);
                BallThread thread = new BallThread(b);
                thread.setPriority(1);
                thread.start();
                storage.add(b, thread);

                System.out.println("Thread name = " +
                        thread.getName());
            }

        });
        buttonPanel.add(buttonStart);
    }

    private void stopButton(JPanel buttonPanel) {
        JButton buttonStop = new JButton("Stop");
        buttonStop.addActionListener(e -> System.exit(0));
        buttonPanel.add(buttonStop);
    }
}