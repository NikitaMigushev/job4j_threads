package ru.job4j.threadschildt;

public class PC {
    public static void main(String[] args) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
        System.out.println("Для остановки нажимет Ctrl + C");
    }
}
