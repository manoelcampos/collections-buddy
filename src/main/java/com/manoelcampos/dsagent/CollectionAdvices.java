package com.manoelcampos.dsagent;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * https://github.com/hxt970311/aspectj-ltw
 */
public class CollectionAdvices {
    public static final Map<Method, Integer> calls = new HashMap<>();

    /**
     * Executed when an advised method is called
     * @param thisObject object that called the method
     * @param origin a String representing the called method signature
     * @param args arguments given to the called method
     * @return the time the method started, automatically used as input param to exit() method
     */
    @Advice.OnMethodEnter(suppress = Throwable.class)
    static long enter(@Advice.This Object thisObject,
                      @Advice.Origin String origin,
                      //@Advice.Origin("#t #m") String detailedOrigin,
                      @Advice.AllArguments Object[] args)
    {
        //System.out.println("Called: " + origin);
        return System.nanoTime();
    }

    /**
     * Executed when an advised method is finished.
     * @param startTime the time the method was started, which automatically comes from the
     *                  return of enter() method.
     */
    @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
    static void exit(@Advice.Origin String origin, @Advice.Enter long startTime){
        System.out.printf("Execution Time: %10dns for %s%n", (System.nanoTime() - startTime), origin);
    }
}
