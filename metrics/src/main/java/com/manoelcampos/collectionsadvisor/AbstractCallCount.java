package com.manoelcampos.collectionsadvisor;

import java.util.Objects;

public abstract class AbstractCallCount {
    private int calls;
    private String operation;

    public AbstractCallCount(final String operation) {
        this.operation = Objects.requireNonNull(operation);
    }

    /**
     * Gets the number of calls for the {@link #operation}
     * that affected the collection.
     */
    public int getCalls() {
        return calls;
    }

    protected void incCalls(){
        calls++;
    }

    public abstract void track(CollectionCall call);

    public String getOperation() {
        return operation;
    }
}
