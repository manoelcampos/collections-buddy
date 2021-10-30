package com.sample;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * An example incorrectly using an ArrayList as a queue (FIFO policy)
 */
public class ArrayListAsQueueSample {
    public ArrayListAsQueueSample() {
        final var list = new ArrayList<Integer>();
        tailInsert(list, 10);
        headRemoval(list);
    }

    private void tailInsert(final List<Integer> list, final int count) {
        for (int value = 0; value < count; value++) {
            list.add(value);
        }

        print(list);
    }

    private void headRemoval(final List<Integer> list) {
        while(!list.isEmpty()){
            list.remove(0);
        }
    }

    private void print(final List<Integer> list) {
        final String items = list.stream().map(String::valueOf).collect(joining(" "));
        System.out.printf("%s.%s: %s%n", getClass().getSimpleName(), list.getClass().getSimpleName(), items);
    }
}
