package com.manoelcampos.collectionsadvisor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to store metrics about calls to methods on JDK {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class Metrics {
    /**
     * Only {@link Collection} objects declared in classes
     * inside this package will be traced.
     * That can be changed by passing a new value as a command line parameter to the Collections Advisor Agent.
     * Check CollectionAgent class documentation in the agent project dir.
     */
    public static String INSPECT_PACKAGE_NAME = "com.manoelcampos.collectionsadvisor";

    /** @see #getMetricMap() */
    private static final Map<String, Integer> metricMap = new HashMap<>();

    public static void print() {
        System.out.printf("%n# %d Metrics%n", metricMap.size());
        metricMap.forEach((k, calls) -> System.out.printf("%s: %d calls%n", k, calls));
    }

    /**
     * A map where keys are a reference for methods called from a {@link Collection}
     * and values are the number of calls for each method.
     */
    public static Map<String, Integer> getMetricMap() {
        return Collections.unmodifiableMap(metricMap);
    }

    /**
     * Increments the number of calls for a given method
     * @param methodReference a String representation of the {@link Collection} method called
     * @return the new number of calls for the given Collection method
     */
    public static int add(final String methodReference){
        final int calls = metricMap.getOrDefault(methodReference, 0) + 1;
        metricMap.put(methodReference, calls);
        return calls;
    }
}
