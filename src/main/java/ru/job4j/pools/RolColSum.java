package ru.job4j.pools;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            int colSum = 0;
            int rowSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        ExecutorService executor = Executors.newFixedThreadPool(size);
        Future<Integer>[] rowSumFutures = new Future[size];
        for (int i = 0; i < size; i++) {
            final int rowIndex = i;
            rowSumFutures[i] = executor.submit(() -> {
                int rowSum = 0;
                for (int j = 0; j < size; j++) {
                    rowSum += matrix[rowIndex][j];
                }
                return rowSum;
            });
        }
        for (int i = 0; i < size; i++) {
            int colSum = 0;
            try {
                colSum = calcColSum(matrix, i);
                sums[i] = new Sums(rowSumFutures[i].get(), colSum);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return sums;
    }

    private static int calcColSum(int[][] matrix, int col) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][col];
        }
        return sum;
    }
}
