package com.manoelcampos.sample;

import java.util.Random;
import java.util.LinkedList;

/**
 * A sample to check that method calls on JDK {@link java.util.Collection} classes
 * are being intercepted by the implemented Collections Advisor Agent.
 * @author Manoel Campos da Silva Filho
 */
public class Main {
    private final LinkedList<Integer> list;

    public Main(){
        System.out.printf("%nStarting %s%n", Main.class.getName());
        this.list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        final Random rand = new Random();
        for (int i = 0; i < 200; i++) {
            list.get(rand.nextInt(list.size()));
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
