package com.manoelcampos.collectionsbuddy;

import net.bytebuddy.asm.Advice;

import java.util.*;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionAdvices {
    /**
     * A map where keys are a reference for methods called from a {@link Collection}
     * and values are the number of calls for each method.
     */
    public static final Map<String, Integer> metricMap = new HashMap<>();

    /**
     * Only {@link Collection} objects declared in classes
     * inside this package will be traced.
     */
    public static final String INSPECT_PACKAGE_NAME = "com.manoelcampos";

    /**
     * Executed when an advised method is called.
     * @return the time the method started, automatically used as input param to exit() method
     */
    @Advice.OnMethodEnter(suppress = Throwable.class)
    static long enter() {
        return System.nanoTime();
    }

    /**
     * Executed when an advised method is finished.
     * @param origin identifies which advised method was called and from where
     * @param startTime the time the method was started, which automatically comes from the
     *                  return of enter() method.
     */
    @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
    static void exit(
        final @Advice.Origin(value = "#t.#m#s") String origin,
        final @Advice.Enter long startTime)
    {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var callerClass = walker.getCallerClass();
        final var fullOrigin = String.format("%s from %s", origin, callerClass.getName());
        if(callerClass.getName().startsWith(INSPECT_PACKAGE_NAME)) {
            System.out.println(fullOrigin);
            System.out.println("Current metrics map size: " + metricMap);
            metricMap.compute(fullOrigin, (k, v) -> v == null ? 1 : v+1);
        }
    }

    public static void printMetrics() {
        System.out.printf("%n# %d Metrics%n", metricMap.size());
        metricMap.forEach((k, calls) -> System.out.printf("%s: %d calls%n", k, calls));
    }
}
