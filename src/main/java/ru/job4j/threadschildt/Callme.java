package ru.job4j.threadschildt;

public class Callme {
    private int currentOrder = 1;
    synchronized void call(String msg, int order) {
        while (currentOrder != order) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Прервано");
            }
        }
        System.out.print("[" + msg + "]");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentOrder++;
        notifyAll();
    }
}
