package ru.job4j.threadschildt;

public class Synch {
    public static void main(String[] args) {
        Callme target = new Callme();
        Caller ob1 = new Caller(target, "Добро пожаловать", 1);
        Caller ob2 = new Caller(target, "в синхронизированный", 2);
        Caller ob3 = new Caller(target, "мир!", 3);
        try {
            ob1.t.join();
            ob2.t.join();
            ob3.t.join();
        } catch (InterruptedException e) {
            System.out.println("Прервано");
        }
    }
}
