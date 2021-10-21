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
     * @param methodSignature a String signature of the {@link Collection} method called
     */
    @Advice.OnMethodExit()
    public static void exit(final @Advice.Origin(value = "#t.#m#s") String methodSignature) {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var method = new MethodReference(walker.getCallerClass(), methodSignature);
        Metrics.add(method);
    }
}
