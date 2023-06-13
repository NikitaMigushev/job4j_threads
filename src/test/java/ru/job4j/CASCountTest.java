package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    public void testIncrementAndGet() {
        CASCount casCount = new CASCount();

        casCount.increment();
        assertThat(casCount.get()).isEqualTo(1);

        casCount.increment();
        assertThat(casCount.get()).isEqualTo(2);
    }

    @Test
    public void testConcurrentIncrementAndGet() throws InterruptedException {
        CASCount casCount = new CASCount();
        int numThreads = 10;
        int numIncrementsPerThread = 1000;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < numIncrementsPerThread; j++) {
                    casCount.increment();
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertThat(casCount.get()).isEqualTo(numThreads * numIncrementsPerThread);
    }
}