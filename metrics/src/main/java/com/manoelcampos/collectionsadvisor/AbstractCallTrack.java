package com.manoelcampos.collectionsadvisor;

import java.util.Objects;

/**
 * An abstract class that tracks calls to a specific collection method.
 * @author Manoel Campos da Silva Filho
 */
abstract class AbstractCallTrack {
    private int calls;
    private String operation;

    /**
     * Instantiates an object.
     * @param operation the description of the collection operation to track
     */
    protected AbstractCallTrack(final String operation) {
        this.operation = Objects.requireNonNull(operation);
    }

    /**
     * Gets the number of calls for the tracked {@link #getOperation() method}
     * that caused the collection to be changed.
     */
    public int getCalls() {
        return calls;
    }

    protected void incCalls(){
        calls++;
    }

    /**
     * Collect data about the tracked Collection {@link #getOperation() operations}.
     * @param call information about the collection method call
     */
    public abstract void track(CollectionCall call);

    /**
     * Gets the description of the collection operation to track.
     * @return
     */
    public String getOperation() {
        return operation;
    }
}
