package com.manoelcampos.collectionsadvisor;

import java.util.Objects;

/**
 * Tracks calls to methods that add elements to a Collection.
 * Such methods perform operations at the head, middle or tail of the Collection.
 *
 * <p>There are duplicate code between this and {@link RemoveCallTrack}
 * because the instrumentation doesn't accept injection
 * of classes hierarchy.</p>
 *
 * @author Manoel Campos da Silva Filho
 */
public class AddCallTrack {
    protected final CollectionMetric metric;
    private String operation;
    private int calls;
    private int heads;
    private int middles;
    private int tails;

    /**
     * Instantiates an object.
     *
     * @param operation the description of the collection operation to track
     * @param metric    the metric that keeps all tracking data/objects.
     */
    public AddCallTrack(final String operation, final CollectionMetric metric) {
        this.operation = Objects.requireNonNull(operation);
        this.metric = Objects.requireNonNull(metric);
    }

    /**
     * Gets the position of the element added/removed from the Collection.
     *
     * @param call a reference to the called method.
     * @return
     */
    protected int getIndex(final CollectionCall call) {
        return call.isSingleArg() ? metric.getSize().getPrevious() : (int) call.getArg(0);
    }

    /**
     * Checks if the method call operated over the tail of the Collection.
     *
     * @param call a reference to the called method.
     * @return
     */
    protected boolean isLastIndex(final CollectionCall call) {
        return getIndex(call) == metric.getSize().getCurrent() - 1;
    }

    /**
     * Gets the number of calls that operated over the head of the collection.
     */
    public int getHeads() {
        return heads;
    }

    /**
     * Gets the number of calls that operated in the middle of the collection.
     */
    public int getMiddles() {
        return middles;
    }

    /**
     * Gets the number of calls that operated over the tail of the collection.
     */
    public int getTails() {
        return tails;
    }

    /**
     * Collect data about the tracked Collection {@link #getOperation() operations}.
     *
     * @param call information about the collection method call
     */
    public void track(final CollectionCall call) {
        incCalls();
        if (isLastIndex(call))
            this.tails++;
        else if (isFirstIndex(call))
            this.heads++;
        else this.middles++;
    }

    /**
     * Checks if the method call operated over the head of the Collection.
     *
     * @param call a reference to the called method.
     * @return
     */
    private boolean isFirstIndex(final CollectionCall call) {
        return getIndex(call) == 0;
    }

    @Override
    public String toString() {
        return String.format(
                "%s: %d -> head %d middle %d tail %d", getOperation(), getCalls(),
                heads, middles, tails);
    }

    /**
     * Gets the number of calls for the tracked {@link #getOperation() method}
     * that caused the collection to be changed.
     */
    public int getCalls() {
        return calls;
    }

    protected void incCalls() {
        calls++;
    }

    /**
     * Gets the description of the collection operation to track.
     *
     * @return
     */
    public String getOperation() {
        return operation;
    }
}
