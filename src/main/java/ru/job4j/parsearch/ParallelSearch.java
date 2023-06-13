package ru.job4j.parsearch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch {
    public static <T> int parallelIndexOf(T[] array, T target) {
        if (array.length <= 10) {
            return linearIndexOf(array, target);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SearchTask<T> searchTask = new SearchTask<>(array, target, 0, array.length);
        return forkJoinPool.invoke(searchTask);
    }

    private static <T> int linearIndexOf(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private static class SearchTask<T> extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 10;
        private final T[] array;
        private final T target;
        private final int start;
        private final int end;

        public SearchTask(T[] array, T target, int start, int end) {
            this.array = array;
            this.target = target;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= THRESHOLD) {
                return linearIndexOfRange(array, target, start, end);
            } else {
                int mid = (start + end) / 2;
                SearchTask<T> leftTask = new SearchTask<>(array, target, start, mid);
                SearchTask<T> rightTask = new SearchTask<>(array, target, mid, end);
                leftTask.fork();
                int rightResult = rightTask.compute();
                int leftResult = leftTask.join();
                if (leftResult != -1) {
                    return leftResult;
                } else {
                    return rightResult;
                }
            }
        }

        private int linearIndexOfRange(T[] array, T target, int start, int end) {
            for (int i = start; i < end; i++) {
                if (array[i] != null && array[i].equals(target)) {
                    return i;
                }
            }
            return -1;
        }
    }
}
