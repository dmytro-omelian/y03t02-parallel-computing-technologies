package org.assignment.fox;

public class FoxSync {
    private final double[][][][] aBlocks;
    private final double[][][][] bBlocks;
    private final FoxThread[][] threads;
    private int pausedThreads = 0;
    private int finishedIterations = 0;

    public FoxSync(double[][][][] aBlocks, double[][][][] bBlocks, FoxThread[][] threads) {
        this.aBlocks = aBlocks;
        this.bBlocks = bBlocks;
        this.threads = threads;
    }

    public synchronized void waitForNextIteration(int iteration) {
        this.pausedThreads++;

        while (pausedThreads < getTotalThreads()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            if (iteration < finishedIterations) {
                break;
            }
        }

        if (pausedThreads == getTotalThreads()) {
            updateThreads(iteration);
            pausedThreads = 0;
            finishedIterations++;
            notifyAll();
        }
    }

    private void updateThreads(int iteration) {
        int threadRows = threads.length;
        int threadColumns = threads[0].length;

        for (int i = 0; i < threadRows; i++) {
            int k = (i + 1 + iteration) % threadRows;

            for (int j = 0; j < threadColumns; j++) {
                FoxThread thread = threads[i][j];
                thread.setMatrixA(aBlocks[i][k]);
                thread.setMatrixB(bBlocks[k][j]);
            }
        }
    }

    private int getTotalThreads() {
        return threads.length * threads[0].length;
    }
}
