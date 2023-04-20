package org.example;

import org.example.services.MatrixService;
import org.example.strategies.CalculationStrategy;
import org.example.strategies.MultiplyOperation;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) throws Exception {
        int n = 100;

        MatrixService matrixService = new MatrixService();

        double[][] B = matrixService.generate(n, n);

        double[][] MC = matrixService.generate(n, n);

        long start = System.currentTimeMillis();
        double[][] foxResult = matrixService.multiplyMatrixFox(B, MC, 25).getResult();
        long end = System.currentTimeMillis();

        print("result: ", foxResult);
        System.out.printf("Time: %d ms\n", (end - start));

        CalculationStrategy multiplication = new MultiplyOperation();

        start = System.currentTimeMillis();
        double[][] E = matrixService.processMatrices(B, MC, multiplication);
        end = System.currentTimeMillis();

        print("E=", E);
        System.out.printf("Time: %d ms\n", (end - start));
    }

    private static void print(String message, double[][] result) {
        System.out.println(message);
        System.out.println("n=" + result.length + " m=" + result[0].length);
        for (double[] doubles : result) {
            Arrays.stream(doubles).mapToObj(aDouble -> aDouble + " ").forEach(System.out::print);
            System.out.println();
        }
    }
}
