package ru.job4j.parsearch;

public class Main {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int targetIndex = ParallelSearch.parallelIndexOf(array, 7);
        System.out.println("Target index: " + targetIndex);
    }
}
