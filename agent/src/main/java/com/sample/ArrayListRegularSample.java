package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.joining;

/**
 * Usual ArrayList operations that just adds items to the tail
 * and which don't cause too many resizes.
 */
public class ArrayListRegularSample {
    public ArrayListRegularSample() {
        final var list = new ArrayList<Integer>();
        operate(list, 10);
        list.remove(list.size()-1);
    }

    private void operate(final List<Integer> list, final int count) {
        for (int value = 0; value < count; value++) {
            list.add(value);
        }

        final var rand = new Random();
        final int size = list.size();
        for (int i = 0; i < count; i++) {
            list.get(rand.nextInt(size));
        }

        print(list);
    }

    private void print(final List<Integer> list) {
        final String items = list.stream().map(String::valueOf).collect(joining(" "));
        System.out.printf("%s.%s: %s%n", getClass().getSimpleName(), list.getClass().getSimpleName(), items);
    }
}
