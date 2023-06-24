package org.assignment.fox;

public class FoxThread extends Thread {
    private final double[][] resultMatrix;
    private final int iterationCount;
    private final FoxSync sync;
    private double[][] matrixA;
    private double[][] matrixB;

    public FoxThread(double[][] matrixA, double[][] matrixB, int iterationCount, FoxSync sync) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = new double[matrixA.length][matrixA.length];
        this.iterationCount = iterationCount;
        this.sync = sync;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterationCount; i++) {
            calculateSubtaskIteration();
            sync.waitForNextIteration(i);
        }
    }

    private void calculateSubtaskIteration() {
        for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixB.length; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }

    public double[][] getResultMatrix() {
        return resultMatrix;
    }

    public void setMatrixA(double[][] matrixA) {
        this.matrixA = matrixA;
    }

    public void setMatrixB(double[][] matrixB) {
        this.matrixB = matrixB;
    }
}
