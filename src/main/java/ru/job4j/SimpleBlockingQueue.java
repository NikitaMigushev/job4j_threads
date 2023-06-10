package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int maxSize;
    private final Object monitor = new Object();

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() >= maxSize) {
                monitor.wait();
            }
            queue.offer(value);
            monitor.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (queue.isEmpty()) {
                monitor.wait();
            }
            T value = queue.poll();
            monitor.notifyAll();
            return value;
        }
    }

    public boolean isEmpty() {
        synchronized (monitor) {
            return queue.isEmpty();
        }
    }
}
