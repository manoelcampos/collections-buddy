package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.joining;

/**
 * ArrayList operations that mostly adds items to the head,
 * causing lots of items to be moved.
 */
public class ArrayListHeadAddSample {
    public ArrayListHeadAddSample() {
        final var list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(0, i);
        }

        print(list);
    }

    private void print(final List<Integer> list) {
        final String items = list.stream().map(String::valueOf).collect(joining(" "));
        System.out.printf("%s.%s: %s%n", getClass().getSimpleName(), list.getClass().getSimpleName(), items);
    }

    /**
     * Inserts the value of the index in the same position it represents
     * @param index the value and position to add
     */
    void add(final List<Integer> list, final int index){
        list.add(index, index);
    }
}
