package ru.job4j.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    private Cache cache;
    private Base base;

    @BeforeEach
    public void setUp() {
        cache = new Cache();
        base = new Base(1, 1);
        base.setName("Test");
        cache.add(base);
    }

    @Test
    public void getValueFromCache() {
        Base result = cache.get(1);
        assertThat(result).isEqualTo(base);
    }

    @Test
    public void whenUpdateNameVersionIncrement() {
        Base updateModel = new Base(1, 1);
        updateModel.setName("Test2");
        cache.update(updateModel);
        assertThat(updateModel.getVersion()).isEqualTo(2);
    }

    @Test
    public void whenUpdateSameVersionIncrement() {
        Base updateModel = new Base(1, 1);
        cache.update(updateModel);
        assertThat(updateModel.getVersion()).isEqualTo(2);
    }

    @Test
    public void whenTwoUpdatesLastWins() {
        Base updateModelA = new Base(1, 1);
        updateModelA.setName("TestA");
        Base updateModelB = new Base(1, 2);
        updateModelB.setName("TestB");
        cache.update(updateModelA);
        cache.update(updateModelB);
        assertThat(cache.get(1).getName()).isEqualTo("TestB");
        assertThat(cache.get(1).getVersion()).isEqualTo(3);
    }

    @Test
    public void whenVersionsNotEqualThrowException() {
        Base updateModelA = new Base(1, 1);
        updateModelA.setName("TestA");
        Base updateModelB = new Base(1, 1);
        updateModelA.setName("TestB");
        cache.update(updateModelA);
        assertThatThrownBy(() -> cache.update(updateModelB))
                .isInstanceOf(OptimisticException.class)
                .hasMessage("Versions are not equal");
    }

    @Test
    public void whenDeleteSuccess() {
        Base updateModelA = new Base(1, 1);
        updateModelA.setName("TestA");
        Base updateModelB = new Base(1, 2);
        updateModelB.setName("TestB");
        cache.update(updateModelA);
        cache.update(updateModelB);
        boolean result = cache.delete(updateModelB);
        assertThat(result).isTrue();
    }

    @Test
    public void whenDeleteThrowsError() {
        Base updateModelA = new Base(1, 1);
        updateModelA.setName("TestA");
        Base updateModelB = new Base(1, 1);
        updateModelB.setName("TestB");
        cache.update(updateModelA);
        assertThatThrownBy(() -> cache.delete(updateModelB))
                .isInstanceOf(OptimisticException.class)
                .hasMessage("Versions are not equal");
    }


}