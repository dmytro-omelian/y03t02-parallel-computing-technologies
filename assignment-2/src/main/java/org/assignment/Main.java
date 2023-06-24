package org.assignment;

import org.assignment.entity.Matrix;
import org.assignment.fox.FoxMatrixMultiplicator;

public class Main {

    public static void main(String[] args) {
        final int defaultMatrixSize = 1000;
        final int defaultFoxBlocks = 2;

        testParallelAlgorithmCorrectness(defaultMatrixSize, defaultFoxBlocks);

        final int[] testMatrixSizesData = {500, 750, 1000, 1250, 1500, 2000};
        testMatrixSizes(testMatrixSizesData, defaultFoxBlocks);

        final int[] testThreadNumsData = {2, 4, 6, 8, 9, 12, 15, 16, 25};
        testThreadsNums(defaultMatrixSize, testThreadNumsData);
    }

    private static void testParallelAlgorithmCorrectness(int matrixSize, int foxBlocks) {
        final Matrix matrixA = new Matrix(matrixSize);
        final Matrix matrixB = new Matrix(matrixSize);
        matrixA.generateRandomMatrix(1, 100);
        matrixB.generateRandomMatrix(1, 100);
        matrixA.saveToCSV("matrix_a");
        matrixB.saveToCSV("matrix_b");

        FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(foxBlocks);

        Matrix resultNaive = matrixA.multiply(matrixB);
        Matrix resultFox = foxMatrixMultiplicator.multiply(matrixA, matrixB);
        System.out.println("Parallel algorithm is correct: " + resultNaive.equals(resultFox));
    }

    private static void testMatrixSizes(int[] sizes, int foxBlocksNum) {
        long[] naiveMetrics = new long[sizes.length];
        long[] foxMetrics = new long[sizes.length];

        for (int i = 0; i < sizes.length; i++) {
            Matrix matrixA = new Matrix(sizes[i]);
            Matrix matrixB = new Matrix(sizes[i]);
            matrixA.generateRandomMatrix(1, 100);
            matrixB.generateRandomMatrix(1, 100);

            long start = System.nanoTime();
            matrixA.multiply(matrixB);
            long finish = System.nanoTime();
            naiveMetrics[i] = finish - start;

            FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(foxBlocksNum);
            start = System.nanoTime();
            foxMatrixMultiplicator.multiply(matrixA, matrixB);
            finish = System.nanoTime();
            foxMetrics[i] = finish - start;
        }

        System.out.println("SUBTASK 3: SIZE TESTING (Threads num is " + foxBlocksNum * foxBlocksNum + ")");
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("-------- Size: " + sizes[i] + " --------");
            System.out.println("Naive: " + naiveMetrics[i] + " ns");
            System.out.println("Fox: " + foxMetrics[i] + " ns, Speedup: " + naiveMetrics[i] * 1.0 / foxMetrics[i]);
        }
    }

    private static void testThreadsNums(int matrixSize, int[] threadsNums) {
        long[] naiveMetrics = new long[threadsNums.length];
        long[] foxMetrics = new long[threadsNums.length];

        final Matrix matrixA = new Matrix(matrixSize);
        final Matrix matrixB = new Matrix(matrixSize);
        matrixA.generateRandomMatrix(1, 100);
        matrixB.generateRandomMatrix(1, 100);

        for (int i = 0; i < threadsNums.length; i++) {
            long start = System.nanoTime();
            matrixA.multiply(matrixB);
            long finish = System.nanoTime();
            naiveMetrics[i] = finish - start;

            int sqrtThreads = (int) Math.sqrt(threadsNums[i]);
            if (Math.pow(sqrtThreads, 2) == threadsNums[i]) {
                FoxMatrixMultiplicator foxMatrixMultiplicator = new FoxMatrixMultiplicator(sqrtThreads);
                start = System.nanoTime();
                foxMatrixMultiplicator.multiply(matrixA, matrixB);
                finish = System.nanoTime();
                foxMetrics[i] = finish - start;
            }
        }

        System.out.println("SUBTASK 4: THREAD NUM TESTING (Matrix size is " + matrixSize + ")");
        for (int i = 0; i < threadsNums.length; i++) {
            System.out.println("-------- Threads num: " + threadsNums[i] + " --------");
            System.out.println("Naive: " + naiveMetrics[i] + " ns");
            if (foxMetrics[i] == 0) {
                System.out.println("Fox: the number of threads is not square");
            } else {
                System.out.println("Fox: " + foxMetrics[i] + " ns, Speedup: " + naiveMetrics[i] * 1.0 / foxMetrics[i]);
            }
        }
    }
}
