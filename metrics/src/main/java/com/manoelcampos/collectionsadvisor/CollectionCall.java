package com.manoelcampos.collectionsadvisor;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * Represents data about a call for a {@link Collection} method.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionCall {
    /**
     * Current size of the collection.
     */
    private int size;
    private final Class<?> callerClass;
    private final String collectionClass;
    private final String collectionMethod;
    private final Collection<?> collection;
    private final int previousSize;
    private final CollectionReference collectionReference;

    public CollectionCall(
        final Class<?> callerClass,
        final String collectionClass,
        final String collectionMethod,
        final Collection<?> collection,
        final int previousSize,
        final int currentsSize)
    {
        this.callerClass = requireNonNull(callerClass);
        this.collectionClass = requireNonNull(collectionClass);
        this.collectionMethod = requireNonNull(collectionMethod);
        this.collection = collection;
        this.size = currentsSize;
        this.previousSize = previousSize;

        this.collectionReference = new CollectionReference(callerClass, collectionClass);
    }

    public String getCollectionClass() {
        return collectionClass;
    }

    public String getCollectionMethod() {
        return collectionMethod;
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
