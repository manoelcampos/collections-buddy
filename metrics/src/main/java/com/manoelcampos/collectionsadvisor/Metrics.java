package com.manoelcampos.collectionsadvisor;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Class to store metrics about calls to methods on JDK {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class Metrics {
    /** @see #getTracedPackageName() */
    private static String tracedPackageName = "com.manoelcampos.collectionsadvisor";

    /** @see #getMetricMap() */
    private static final Map<MethodReference, Integer> metricMap = new HashMap<>();

    /**
     * A map where keys are a reference for methods called from a {@link Collection}
     * and values are the number of calls for each method.
     */
    public static Map<MethodReference, Integer> getMetricMap() {
        return Collections.unmodifiableMap(metricMap);
    }

    public static void print() {
        if(metricMap.isEmpty()){
            System.out.println();
            System.out.println("# Collections Advisor Agent :: No Intercepted calls to java.util.Collection methods. ");
            System.out.println("# This may be because:");
            System.out.println("  - the agent is not working;");
            System.out.println("  - you don't have code using Collection classes;");
            System.out.println("  - you haven't correctly specified the package to track Collection calls when starting up the agent for you app.");
            return;
        }

        System.out.printf(
            "%n# Collections Advisor Agent :: Intercepted java.util.Collection methods calls from %s package%n",
            tracedPackageName);
        metricMap.forEach((method, calls) -> System.out.printf("%s: %d %s%n", method, calls, calls > 1 ? "calls" : "call"));
    }

    /**
     * Gets the name of the package to trace calls to {@link Collection} methods.
     * @see #setTracedPackageName(String)
     */
    public static String getTracedPackageName() {
        return tracedPackageName;
    }

    /**
     * Sets the name of the package to trace calls to {@link Collection} methods.
     * Only {@link Collection} objects declared in classes
     * inside this package will be traced.
     * That can be changed by passing a new value as a command line parameter to the Collections Advisor Agent.
     * Check CollectionAgent class documentation in the agent project dir.
     * @param tracedPackageName the package to inspect
     */
    public static void setTracedPackageName(final String tracedPackageName) {
        if(requireNonNull(tracedPackageName).isBlank()){
            throw new IllegalArgumentException("Inspect package name cannot be blank");
        }

        Metrics.tracedPackageName = tracedPackageName;
    }

    /**
     * Increments the number of calls for a given method
     * @param method a reference to the {@link Collection} method called
     * @return the new number of calls for the given Collection method or 0
     *         if the method {@link MethodReference#isNotInsideTracedPackage() isn't inside a traced package}
     */
    public static int add(final MethodReference method){
        if (requireNonNull(method).isNotInsideTracedPackage()) {
            return 0;
        }

        final int calls = metricMap.getOrDefault(method, 0) + 1;
        metricMap.put(method, calls);
        return calls;
    }
}
