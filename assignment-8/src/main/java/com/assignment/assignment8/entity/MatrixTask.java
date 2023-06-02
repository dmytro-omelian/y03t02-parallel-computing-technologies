package com.assignment.assignment8.entity;


import com.assignment.assignment8.exception.FullyProcessedException;
import com.assignment.assignment8.service.MatrixService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.RecursiveAction;

public class MatrixTask extends RecursiveAction {
    private static final Logger logger = LogManager.getLogger(MatrixTask.class);

    private final MatrixService matrixService;

    private final double[][] A;
    private final double[][] B;
    private final double[][] result;

    private final ConcurrencyContext context;

    public MatrixTask(double[][] A, double[][] B, double[][] result, ConcurrencyContext context, MatrixService matrixService) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null.");
        }
        this.A = A;
        this.B = B;
        this.result = result;
        this.context = context;
        this.matrixService = matrixService;
    }

    @Override
    protected void compute() {
        if (context.isFullyProcessed()) {
            return;
        }

        try {
            int row = context.nextRowNumber();
            logger.info(Thread.currentThread().getName() + " is going to process row " + row);
            this.matrixService.calculate(A, B, result, row);

            invokeAll(new MatrixTask(A, B, result, context, matrixService));
        } catch (FullyProcessedException ignored) {
        }
    }


    public static class ConcurrencyContext {
        private final int numberOfRows;
        private int currentNumber;

        public ConcurrencyContext(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            this.currentNumber = 0;
        }

        public synchronized int nextRowNumber() throws FullyProcessedException {
            if (isFullyProcessed()) {
                throw new FullyProcessedException("Already fully processed");
            }
            return this.currentNumber++;
        }

        public synchronized boolean isFullyProcessed() {
            return this.currentNumber == this.numberOfRows;
        }

    }

}
