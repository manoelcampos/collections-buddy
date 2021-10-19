package com.manoelcampos.collectionsadvisor;

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
    public static String INSPECT_PACKAGE_NAME = "com.manoelcampos.collectionsadvisor";

    /**
     * A map where keys are a reference for methods called from a {@link Collection}
     * and values are the number of calls for each method.
     */
    public static final Map<String, Integer> metricMap = new HashMap<>();

    /**
     * Executed when an advised method is finished.
     * @param origin identifies which advised method was called and from where
     */
    @Advice.OnMethodExit()
    static void exit(final @Advice.Origin(value = "#t.#m#s") String origin)
    {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var callerClass = walker.getCallerClass().getName();
        final var fullOrigin = String.format("%s from %s", origin, callerClass);
        if(callerClass.startsWith(INSPECT_PACKAGE_NAME)) {
            final Integer calls = metricMap.getOrDefault(fullOrigin, 0) + 1;
            metricMap.put(fullOrigin, calls);
            System.out.printf("Current calls for %s: %d%n", fullOrigin, calls);
        }
    }

    public static void printMetrics() {
        System.out.printf("%n# %d Metrics%n", metricMap.size());
        metricMap.forEach((k, calls) -> System.out.printf("%s: %d calls%n", k, calls));
    }
}
