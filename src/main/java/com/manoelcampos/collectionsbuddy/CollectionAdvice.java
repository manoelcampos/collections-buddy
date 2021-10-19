package com.manoelcampos.collectionsbuddy;

import net.bytebuddy.asm.Advice;

import java.util.*;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionAdvice {
    /**
     * Only {@link Collection} objects declared in classes
     * inside this package will be traced.
     */
    public static final String INSPECT_PACKAGE_NAME = "com.manoelcampos";

    /**
     * A map where keys are a reference for methods called from a {@link Collection}
     * and values are the number of calls for each method.
     */
    public static final Map<String, Integer> metricMap = new HashMap<>();

    /**
     * Executed when an advised method is finished.
     * @param origin identifies which advised method was called and from where
     */
    @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
    static void exit(final @Advice.Origin(value = "#t.#m#s") String origin)
    {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var callerClass = walker.getCallerClass().getName();
        final var fullOrigin = String.format("%s from %s", origin, callerClass);
        if(callerClass.startsWith(INSPECT_PACKAGE_NAME)) {
            System.out.println("Called " + fullOrigin);
            //The print below just appears if I use a local map instead of the static field
            //final var metricMap = new HashMap<String, Integer>();
            metricMap.put(fullOrigin, 1);

            //compute() isn't working anyway (if metricMap is a field or local var)
            //metricMap.compute(fullOrigin, (k, v) -> v == null ? 1 : v+1);
            System.out.println("Current metric map size: " + metricMap.size());
        }
    }

    public static void printMetrics() {
        System.out.printf("%n# %d Metrics%n", metricMap.size());
        metricMap.forEach((k, calls) -> System.out.printf("%s: %d calls%n", k, calls));
    }
}
