package com.manoelcampos.collectionsadvisor;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * Represents data about a call for a {@link Collection} method.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionCall {
    private final Class<?> callerClass;
    private final String collectionClass;
    private final String collectionMethod;

    /** Arguments given to the advised method called. */
    private final Object[] arguments;

    /** Current size of the collection. */
    private int size;

    /** The actual collection where a method was called. */
    private final Collection<?> collection;

    private final int previousSize;
    private final CollectionReference collectionReference;

    public CollectionCall(
        final Class<?> callerClass,
        final String collectionClass,
        final String collectionMethod,
        final Collection<?> collection,
        final int previousSize,
        final int currentsSize,
        final Object[] arguments)
    {
        this.callerClass = requireNonNull(callerClass);
        this.collectionClass = requireNonNull(collectionClass);
        this.collectionMethod = requireNonNull(collectionMethod);
        this.collection = collection;
        this.size = currentsSize;
        this.previousSize = previousSize;
        this.arguments = arguments;

        this.collectionReference = new CollectionReference(callerClass, collectionClass);
    }

    public String getCollectionClass() {
        return collectionClass;
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public boolean isClear(){
        return collectionMethod.equals("clear");
    }

    public boolean isGet(){
        return collectionMethod.equals("get");
    }

    public boolean isAddHead() {
        return isAdd() && isFirstIndex();
    }

    public boolean isAddTail() {
        return isAdd() && isLastIndex();
    }

    private boolean isMiddleIndex() {
        return !isFirstIndex() && !isLastIndex();
    }

    private boolean isFirstIndex() {
        return getAddIndex() == 0;
    }

    private boolean isLastIndex() {
        return getAddIndex() == previousSize;
    }

    /**
     * Gets the position in which an element is being added to the collection,
     * according to the number of parameters and values given to the add method.
     * @return
     */
    private int getAddIndex() {
        return arguments.length == 1 ? previousSize : (int) arguments[0];
    }

    public boolean isAdd() {
        return collectionMethod.equals("add");
    }

    public Collection<?> getCollection() {
        return collection;
    }

    public int getPreviousSize() {
        return previousSize;
    }

    public CollectionReference getCollectionReference() {
        return collectionReference;
    }

    public boolean isSizeChanged(){
        return size != previousSize;
    }

    public boolean isSizeSmaller(){
        return size < previousSize;
    }

    public boolean isSizeLarger(){
        return size > previousSize;
    }
}
