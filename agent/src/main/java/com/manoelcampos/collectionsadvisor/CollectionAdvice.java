package com.manoelcampos.collectionsadvisor;

import net.bytebuddy.asm.Advice;
import java.util.Collection;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionAdvice {

    /**
     * Executed when an advised method is finished.
     * @param origin identifies which advised method was called and from where
     */
    @Advice.OnMethodExit()
    public static void exit(final @Advice.Origin(value = "#t.#m#s") String origin) {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final String callerClass = walker.getCallerClass().getName();
        final var fullOrigin = String.format("%s from %s", origin, callerClass);
        if(callerClass.startsWith(Metrics.INSPECT_PACKAGE_NAME)) {
            final var map = Metrics.metricMap;
            final Integer calls = map.getOrDefault(fullOrigin, 0) + 1;
            map.put(fullOrigin, calls);
        }
    }
}
