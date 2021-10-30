package com.manoelcampos.collectionsadvisor;

import net.bytebuddy.asm.Advice;
import java.util.Collection;
import java.util.List;

/**
 * {@link Advice} class to trace calls to methods on {@link Collection} objects.
 * @author Manoel Campos da Silva Filho
 */
public class ArrayListAdvice {
    /**
     * Executed when an advised method is started.
     * @return Collection attribute values before its method execution,
     * which is automatically given as input parameter to
     * the exit() advice.
     */
    @Advice.OnMethodEnter()
    public static CollectionAttrs enter(
        final @Advice.FieldValue("size") int previousSize,
        final @Advice.FieldValue("elementData") Object[] elementData)
    {
        return new CollectionAttrs(previousSize, elementData.length);
    }

    /**
     * Executed when an advised method is finished.
     * @param collection the Collection object where one of its methods was called
     * @param collectionClass name of the {@link Collection} class where one of its methods was called
     * @param collectionMethod name of the {@link Collection} method called
     * @param attrs Collection attribute values when the called method was started
     * @param arguments arguments given to the advised method called.
     */
    @Advice.OnMethodExit()
    public static void exit(
        final @Advice.This List<?> collection,
        final @Advice.Origin(value = "#t") String collectionClass,
        final @Advice.Origin(value = "#m") String collectionMethod,
        final @Advice.Enter CollectionAttrs attrs,
        final @Advice.FieldValue("size") int currentSize,
        final @Advice.FieldValue("elementData") Object[] currentElementData,
        final @Advice.AllArguments Object[] arguments)
    {
        final var walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        final var collectionRef = new CollectionReference(walker.getCallerClass(), collectionClass);
        final var call = new CollectionCall(collectionRef, collection, collectionMethod, arguments);
        attrs.setCurrentSize(currentSize);
        attrs.setCurrentCapacity(currentElementData.length);
        Metrics.add(call, attrs);
    }
}
