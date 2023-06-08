package ru.job4j.threadschildt;

public class Caller implements Runnable {
    String msg;
    Callme target;
    Thread t;
    int order;

    public Caller(Callme targ, String s, int ord) {
        target = targ;
        msg = s;
        order = ord;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        target.call(msg, order);
    }
}
