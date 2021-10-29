package com.manoelcampos.collectionsadvisor;

import java.util.Objects;

/**
 * An abstract class that tracks calls to a specific Collection method
 * that operate over elements in certain position, such as add or remove methods.
 * Such methods perform operations at the head, middle or tail of the Collection.
 *
 * TODO this class tracks calls to add/remove methods
 * considering there are versions of such methods that receive
 * an index parameter, which is not the case for some Collections
 * such as Map and Set.
 *
 * @author Manoel Campos da Silva Filho
 */
public abstract class AbstractPositionCallTrack extends AbstractCallTrack {
    protected final CollectionMetric metric;
    private int heads;
    private int middles;
    private int tails;

    /**
     * Instantiates an object.
     * @param operation the description of the collection operation to track
     * @param metric the metric that keeps all tracking data/objects.
     */
    public AbstractPositionCallTrack(final String operation, final CollectionMetric metric) {
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

    /**
     * Checks if the method call operated over the head of the Collection.
     * @param call a reference to the called method.
     * @return
     */
    private boolean isFirstIndex(final CollectionCall call) {
        return getIndex(call) == 0;
    }

    /**
     * Gets the position of the element added/removed from the Collection.
     * @param call a reference to the called method.
     * @return
     */
    protected abstract int getIndex(CollectionCall call);

    /**
     * Checks if the method call operated over the tail of the Collection.
     * @param call a reference to the called method.
     * @return
     */
    protected abstract boolean isLastIndex(CollectionCall call);

    @Override
    public String toString() {
        return String.format(
                "%s: %d -> head %d middle %d tail %d", getOperation(), getCalls(),
                heads, middles, tails);
    }
}
