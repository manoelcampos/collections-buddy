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

        linkedListOperations();
        arrayListOperations();

        System.out.println();
        Metrics.print();
    }

    private void linkedListOperations() {
        final var linkedList = new LinkedList<Integer>();
        operateList(linkedList, 10);
        linkedList.clear();
    }

    private void arrayListOperations() {
        final var arrayList = new ArrayList<Integer>();
        operateList(arrayList, 10);
        arrayList.remove(0);
        arrayList.remove(1);
        arrayList.remove(arrayList.size()-1);
    }

    private void operateList(final List<Integer> list, final int count) {
        for (int value = 0; value < count; value++) {
            list.add(value);
        }
        add(list, 0);
        add(list, list.size()/2);
        add(list, list.size()-1);
        add(list,  list.size());

        final var rand = new Random();
        final int size = list.size();
        for (int i = 0; i < count; i++) {
            list.get(rand.nextInt(size));
        }

        for (final Integer val : list) {
            System.out.printf("%d ", val);
        }
        System.out.println();
    }

    /**
     * Inserts the value of the index in the same position it represents
     * @param index the value and position to add
     */
    private void add(final List<Integer> list, final int index){
        list.add(index, index);
    }
}
