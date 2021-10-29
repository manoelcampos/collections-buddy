package com.manoelcampos.collectionsadvisor;

import net.bytebuddy.asm.Advice;
import java.util.Collection;
import java.util.List;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionAdvice {
    /**
     * Executed when an advised method is started.
     * @param self the Collection object where one of its methods was called
     * @return the size of the before its method execution,
     * which is automatically given as input parameter to
     * the exit() advice.
     */
    @Advice.OnMethodEnter()
    public static int enter(final @Advice.This List<?> self, final @Advice.FieldValue("size") int previousSize) {
        return previousSize;
    }

    /**
     * Executed when an advised method is finished.
     * @param self the Collection object where one of its methods was called
     * @param collectionClass name of the {@link Collection} class where one of its methods was called
     * @param collectionMethod name of the {@link Collection} method called
     * @param previousSize the size of the collection when the called method was started
     * @param arguments arguments given to the advised method called.
     */
    @Advice.OnMethodExit()
    public static void exit(
        final @Advice.This List<?> self,
        final @Advice.Origin(value = "#t") String collectionClass,
        final @Advice.Origin(value = "#m") String collectionMethod,
        final @Advice.Enter int previousSize,
        final @Advice.FieldValue("size") int currentSize,
        final @Advice.AllArguments Object[] arguments)
    {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var call =
                new CollectionCall(
                        walker.getCallerClass(),
                        collectionClass, collectionMethod,
                        self,
                        arguments);
        Metrics.add(call, currentSize, previousSize);
    }
}
