package com.sample;

import com.manoelcampos.collectionsadvisor.CollectionAgent;
import com.manoelcampos.collectionsadvisor.Metrics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * A sample to check that method calls on JDK {@link java.util.Collection} classes
 * are being intercepted by the implemented {@link CollectionAgent}.
 * @author Manoel Campos da Silva Filho
 */
public class Sample {
    /**
     * Runs sample code that uses instrumented {@link java.util.Collections}
     * to check if the agent is working.
     */
    public Sample() {
        System.out.printf("%nStarting %s%n", getClass().getName());

        operateList(new LinkedList<>()).clear();
        operateList(new ArrayList<>()).remove(0);

        System.out.println();
        Metrics.print();
    }

    private List<Integer> operateList(final List<Integer> list) {
        for (int value = 0; value < 10; value++) {
            list.add(value);
        }
        add(list, 0);
        add(list, list.size()/2);
        add(list, list.size()-1);
        add(list,  list.size());

        final var rand = new Random();
        final int size = list.size();
        for (int i = 0; i < 10; i++) {
            list.get(rand.nextInt(size));
        }

        for (final Integer val : list) {
            System.out.printf("%d ", val);
        }
        System.out.println();

        return list;
    }

    /**
     * Inserts the value of the index in the same position it represents
     * @param index the value and position to add
     */
    private void add(final List<Integer> list, final int index){
        list.add(index, index);
    }
}
