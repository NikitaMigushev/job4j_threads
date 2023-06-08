package ru.job4j.threadschildt;

public class Producer implements Runnable {
    Q q;

    public Producer(Q q) {
        this.q = q;
        new Thread(this, "Поставщик").start();
    }

    @Override
    public void run() {
        for (int i = 0; i <= 1000; i++) {
            q.put(i);
        }
    }
}
