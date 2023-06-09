package ru.job4j.threadschildt;

public class MultipleThreadDemo implements Runnable {
    String name;
    Thread t;

    public MultipleThreadDemo(String name) {
        this.name = name;
        this.t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            System.out.println(name + " прерван");
        }
        System.out.println(name + " завершен.");
    }

    public static void main(String[] args) {
        new MultipleThreadDemo("Один");
        new MultipleThreadDemo("Два");
        new MultipleThreadDemo("Три");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван");
        }
        System.out.println("Главный поток завершен");
    }
}
