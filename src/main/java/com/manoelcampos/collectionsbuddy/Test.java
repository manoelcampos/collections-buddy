package com.manoelcampos.collectionsbuddy;

import java.util.LinkedList;
import java.util.Random;

/**
 * A sample to check that method calls on JDK {@link java.util.Collection} classes
 * are being intercepted by the implemented {@link CollectionAgent}.
 * @author Manoel Campos da Silva Filho
 */
public class Test {
    public Test() {
        run();
        CollectionAdvices.printMetrics();
    }

    private void run() {
        System.out.printf("%nStarting %s%n", getClass().getName());

        final var list = new LinkedList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        final var rand = new Random();
        for (int i = 0; i < 200; i++) {
            list.get(rand.nextInt(list.size()));
        }
    }
}
