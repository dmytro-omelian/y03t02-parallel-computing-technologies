package org.assignment_1_2;

public class Main {
    public static void main(String[] args) {
//        Thread thread1 = new Thread(new SymbolAsync('-'));
//        Thread thread2 = new Thread(new SymbolAsync('|'));

        Sync permission = new Sync();
        Thread thread1 = new Thread(new SymbolSync('-', permission, true));
        Thread thread2 = new Thread(new SymbolSync('|', permission, false));

        thread1.start();
        thread2.start();
    }
}
