package com.manoelcampos.collectionsbuddy;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} classes.
 */
public class CollectionAdvices {
    public static final Map<Method, Integer> calls = new HashMap<>();

    /**
     * Only {@link Collection} objects declared in classes
     * inside this package will be traced.
     */
    public static final String INSPECT_PACKAGE_NAME = "com.manoelcampos";

    private static String previousPkg = "";

    /**
     * Executed when an advised method is called
     * @param self object from which the advised method was called
     * @param origin a String representing the called method signature
     * @param args arguments given to the called method
     * @return the time the method started, automatically used as input param to exit() method
     */
    @Advice.OnMethodEnter(suppress = Throwable.class)
    static long enter(
        final @Advice.This Object self,
        final @Advice.Origin String origin,
        //final @Advice.Origin("#t #m") String detailedOrigin,
        final @Advice.AllArguments Object[] args)
    {
        //System.out.println("Called: " + origin);
        return System.nanoTime();
    }

    /**
     * Executed when an advised method is finished.
     * @param self object from which the advised method was called
     * @param startTime the time the method was started, which automatically comes from the
     *                  return of enter() method.
     */
    @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
    static void exit(
        final @Advice.This Object self,
        final @Advice.Origin String origin,
        final @Advice.Enter long startTime)
    {
        final long executionTime = System.nanoTime() - startTime;
        System.out.printf("Execution Time: %10dns for %s%n", executionTime, origin);
    }
}
