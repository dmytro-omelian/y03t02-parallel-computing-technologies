package org.assignment_1_2;

public class SymbolAsync implements Runnable {

    private final Character symbol;

    public SymbolAsync(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; ++ i) {
            for (int j = 0; j < 100; ++ j) {
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
