package com.sample;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.joining;

public class LinkedListSample {
    public LinkedListSample() {
        final var list = new LinkedList<Integer>();
        operate(list, 10);
        list.clear();
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
