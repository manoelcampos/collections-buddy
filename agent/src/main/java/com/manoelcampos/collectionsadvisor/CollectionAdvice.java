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
     * @param methodReference a String representation of the {@link Collection} method called
     */
    @Advice.OnMethodExit()
    public static void exit(final @Advice.Origin(value = "#t.#m#s") String methodReference) {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final String callerClass = walker.getCallerClass().getName();
        final var fullOrigin = String.format("%s from %s", methodReference, callerClass);
        if(callerClass.startsWith(Metrics.INSPECT_PACKAGE_NAME)) {
            Metrics.add(fullOrigin);
        }
    }
}
