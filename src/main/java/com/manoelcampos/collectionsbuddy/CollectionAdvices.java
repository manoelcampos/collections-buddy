package com.manoelcampos.collectionsbuddy;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
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
        final long executionTime = System.nanoTime() - startTime;

        /*
         * Extracting a method for this two lines are expected to not work,
         * since the getCallerClass will return this exit method,
         * not the advised method.
         * However, it's strange that the package name check cannot be
         * extracted to a new method, since it stops working.
         */
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var callerClassName = walker.getCallerClass().getName();
        if(callerClassName.startsWith(INSPECT_PACKAGE_NAME)) {
            System.out.printf(
                "Execution Time: %10dns for %s called from object inside %s%n",
                executionTime, origin, callerClassName);
        }
    }
}
