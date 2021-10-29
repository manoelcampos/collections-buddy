package com.manoelcampos.collectionsadvisor;

import java.util.Objects;

/**
 * Counts the number of calls for a given collection method
 * that change the collection size
 * by inserting or removing elements on some position.
 * TODO this class tracks calls to add/remove methods
 * considering there are versions of such methods that receive
 * an index parameter, which is not the case for some collections
 * such as Map and Set.
 */
public abstract class AbstractPositionCallCount extends AbstractCallCount {
    protected final CollectionMetric metric;
    private int heads;
    private int middles;
    private int tails;

    public AbstractPositionCallCount(final String operation, final CollectionMetric metric) {
        super(operation);
        this.metric = Objects.requireNonNull(metric);
    }

    /** Gets the number of calls that operated over the head of the collection. */
    public int getHeads() {
        return heads;
    }

    /** Gets the number of calls that operated in the middle of the collection. */
    public int getMiddles() {
        return middles;
    }

    /** Gets the number of calls that operated over the tail of the collection. */
    public int getTails() {
        return tails;
    }

    @Override
    public void track(final CollectionCall call) {
        incCalls();
        if(isLastIndex(call))
            this.tails++;
        else if(isFirstIndex(call))
            this.heads++;
        else this.middles++;
    }

    private boolean isFirstIndex(final CollectionCall call) {
        return getIndex(call) == 0;
    }

    protected abstract int getIndex(CollectionCall call);

    protected abstract boolean isLastIndex(CollectionCall call);

    @Override
    public String toString() {
        return String.format(
                "%s: %d -> head %d middle %d tail %d", getOperation(), getCalls(),
                heads, middles, tails);
    }
}
