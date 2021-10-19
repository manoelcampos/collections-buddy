package com.manoelcampos.collectionsadvisor;

import java.util.Collection;
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

    /**
     * A map where keys are a reference for methods called from a {@link Collection}
     * and values are the number of calls for each method.
     */
    public static final Map<String, Integer> metricMap = new HashMap<>();

    public static void print() {
        System.out.printf("%n# %d Metrics%n", metricMap.size());
        metricMap.forEach((k, calls) -> System.out.printf("%s: %d calls%n", k, calls));
    }
}
