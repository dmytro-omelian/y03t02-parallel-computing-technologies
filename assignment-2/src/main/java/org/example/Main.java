package org.example;

import org.example.services.MatrixService;
import org.example.strategies.CalculationStrategy;
import org.example.strategies.DiffOperation;
import org.example.strategies.MultiplyOperation;
import org.example.strategies.SumOperation;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        int n = 100;

        MatrixService matrixService = new MatrixService();

        double[][] B = matrixService.generate(1, n);
        double[][] D = matrixService.generate(1, n);

        double[][] b = matrixService.generate(1, 1);

        double[][] MC = matrixService.generate(n, n);
        double[][] MD = matrixService.generate(n, n);
        double[][] MX = matrixService.generate(n, n);

        double[][] minValue = matrixService.min(MC);

        CalculationStrategy multiplication = new MultiplyOperation();
        CalculationStrategy sum = new SumOperation();
        CalculationStrategy difference = new DiffOperation();

        long start = System.currentTimeMillis();

        double[][] E = matrixService.processMatrices(
                matrixService.processMatrices(B, MC, multiplication),
                matrixService.multiplyByNumber(D, minValue),
                sum
        );
        double[][] MA = matrixService.processMatrices(
                matrixService.processMatrices(
                        matrixService.multiplyByNumber(MD, b),
                        matrixService.processMatrices(MC, MX, difference),
                        multiplication
                ),
                matrixService.multiplyByNumber(
                        matrixService.processMatrices(MX, MC, multiplication),
                        b
                ),
                sum
        );

        long end = System.currentTimeMillis();

        print("E=", E);
        print("MA=", MA);
        System.out.println("Time: " + (end - start) + " ms");
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
