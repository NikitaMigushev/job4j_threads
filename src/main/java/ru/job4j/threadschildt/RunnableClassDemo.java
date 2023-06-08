package ru.job4j.threadschildt;

public class RunnableClassDemo implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("I run " + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RunnableClassDemo());
        thread.start();
        thread.join();
        thread.interrupt();
    }
}
