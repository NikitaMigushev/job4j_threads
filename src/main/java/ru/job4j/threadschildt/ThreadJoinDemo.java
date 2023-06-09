package ru.job4j.threadschildt;

public class ThreadJoinDemo implements Runnable {
    String name;
    Thread t;

    public ThreadJoinDemo(String name) {
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
            System.out.println(name + " прерван.");
        }
        System.out.println(name + " завершен.");
    }

    public static void main(String[] args) {
        ThreadJoinDemo t1 = new ThreadJoinDemo("Один");
        ThreadJoinDemo t2 = new ThreadJoinDemo("Два");
        ThreadJoinDemo t3 = new ThreadJoinDemo("Три");
        System.out.println("Поток Один запущен: " + t1.t.isAlive());
        System.out.println("Поток Два запущен: " + t2.t.isAlive());
        System.out.println("Поток Три запущен: " + t3.t.isAlive());

        try {
            System.out.println("Ожидание завершения потоков.");
            t1.t.join();
            t2.t.join();
            t3.t.join();
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван");
        }
        System.out.println("Поток Один запущен: " + t1.t.isAlive());
        System.out.println("Поток Два запущен: " + t2.t.isAlive());
        System.out.println("Поток Три запущен: " + t3.t.isAlive());
        System.out.println("Главный поток завершен.");
    }
}
