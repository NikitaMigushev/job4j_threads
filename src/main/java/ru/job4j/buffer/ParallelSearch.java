package ru.job4j.buffer;

import ru.job4j.SimpleBlockingQueue;

import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        AtomicBoolean isProducerFinished = new AtomicBoolean(false);

        final Thread consumer = new Thread(
                () -> {
                    while (!isProducerFinished.get() || !queue.isEmpty()) {
                        try {
                            Integer value = queue.poll();
                            if (value != null) {
                                System.out.println(value);
                            } else {
                                Thread.sleep(100);
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    isProducerFinished.set(true);
                    consumer.interrupt();
                }
        ).start();
    }
}
