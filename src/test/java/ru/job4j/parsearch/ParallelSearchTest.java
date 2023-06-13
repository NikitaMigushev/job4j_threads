package ru.job4j.parsearch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ParallelSearchTest {
    @Test
    public void whenParallelIndexOfIntegerArrayFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int targetIndex = ParallelSearch.parallelIndexOf(array, 7);
        Assertions.assertThat(targetIndex).isEqualTo(6);
    }

    @Test
    public void whenParallelIndexOfLastIntegerArrayFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int targetIndex = ParallelSearch.parallelIndexOf(array, 10);
        Assertions.assertThat(targetIndex).isEqualTo(9);
    }

    @Test
    public void whenParallelIndexOfIntegerArrayNotFound() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int targetIndex = ParallelSearch.parallelIndexOf(array, 11);
        Assertions.assertThat(targetIndex).isEqualTo(-1);
    }

    @Test
    public void whenParallelIndexOfStringArrayFound() {
        String[] array = {"apple", "banana", "cherry", "date", "elderberry"};
        int targetIndex = ParallelSearch.parallelIndexOf(array, "cherry");
        Assertions.assertThat(targetIndex).isEqualTo(2);
    }

    @Test
    public void whenParallelIndexOfStringArrayNotFound() {
        String[] array = {"apple", "banana", "cherry", "date", "elderberry"};
        int targetIndex = ParallelSearch.parallelIndexOf(array, "grape");
        Assertions.assertThat(targetIndex).isEqualTo(-1);
    }

    @Test
    public void whenParallelIndexOfLargeArrayFound() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int targetIndex = ParallelSearch.parallelIndexOf(array, 500);
        Assertions.assertThat(targetIndex).isEqualTo(500);
    }

    @Test
    public void whenParallelIndexOfLargeArrayNotFound() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        int targetIndex = ParallelSearch.parallelIndexOf(array, 1001);
        Assertions.assertThat(targetIndex).isEqualTo(-1);
    }
}