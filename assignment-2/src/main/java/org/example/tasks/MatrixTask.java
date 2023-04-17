package org.example.tasks;

import org.apache.log4j.Logger;
import org.example.exception.FullyProcessedException;
import org.example.strategies.CalculationStrategy;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;


public class MatrixTask implements Callable<Void> {

    private static final Logger logger = Logger.getLogger(MatrixTask.class);

    private final CalculationStrategy strategy;

    private final double[][] A;
    private final double[][] B;
    private final double[][] result;

    private final ConcurrencyContext context;
    private final Lock lock;

    public MatrixTask(Lock lock, ConcurrencyContext context, double[][] A, double[][] B, double[][] result, CalculationStrategy strategy) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null.");
        }
        this.lock = lock;
        this.A = A;
        this.B = B;
        this.result = result;
        this.context = context;
        this.strategy = strategy;
    }

    @Override
    public Void call() throws Exception {
        boolean proceed = true;
        while (proceed) {
            try {
                lock.lock();
                int row = context.nextRowNumber();
                logger.info(Thread.currentThread().getName() + " is going to process row " + row);
                this.strategy.calculate(A, B, result, row);
            } catch (FullyProcessedException e) {
                proceed = false;
            } finally {
                lock.unlock();
            }
        }
        return null;
    }

    public static class ConcurrencyContext {
        private final int numberOfRows;
        private int currentNumber;

        public ConcurrencyContext(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            this.currentNumber = 0;
        }

        public int nextRowNumber() throws FullyProcessedException {
            if (isFullyProcessed()) {
                throw new FullyProcessedException("Already fully processed");
            }
            return this.currentNumber++;
        }

        public boolean isFullyProcessed() {
            return this.currentNumber == this.numberOfRows;
        }

    }

}

