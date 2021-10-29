package com.manoelcampos.collectionsadvisor;

import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * Represents data that identifies a call to a {@link Collection} method.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionCall {
    private final String collectionMethod;

    /**
     * Argumentos given to the Collection method called.
     */
    private final Object[] arguments;

    /** The actual Collection where a method was called. */
    private final Collection<?> collection;

    private final CollectionReference collectionReference;

    public CollectionCall(
        final Class<?> callerClass,
        final Collection<?> collection,
        final String collectionClass,
        final String collectionMethod,
        final Object[] arguments)
    {
        this.collectionMethod = requireNonNull(collectionMethod);
        this.collection = collection;
        this.arguments = arguments;
        this.collectionReference = new CollectionReference(callerClass, collectionClass);
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

    public boolean isRemove(){
        return collectionMethod.equals("remove");
    }

    public boolean isAdd() {
        return collectionMethod.equals("add");
    }

    public Collection<?> getCollection() {
        return collection;
    }

    public CollectionReference getCollectionReference() {
        return collectionReference;
    }

    /** Gets the number of argumentos given to the collection method called. */
    public int getArgsLen() {
        return arguments.length;
    }

    /** Gets an argument given to the collection method called.
     * @param position index of the argument to get from the arguments
     *                 given to the advised method
     * */
    public Object getArg(final int position) {
        return arguments[position];
    }

    public boolean isSingleArg(){
        return arguments.length == 1;
    }
}
