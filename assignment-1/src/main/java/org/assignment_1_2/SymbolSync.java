package org.assignment_1_2;

public class SymbolSync implements Runnable {

    private final Character symbol;
    private final Sync permission;
    private final boolean control;

    public SymbolSync(char symbol, Sync permission, boolean control) {
        this.symbol = symbol;
        this.permission = permission;
        this.control = control;
    }

    @Override
    public void run() {
        while (true) {
            permission.waitAndChange(control, symbol);
            if (permission.isStop()) return;
        }
    }

}
