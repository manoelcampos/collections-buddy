package com.manoelcampos.collectionsadvisor;

import java.util.Objects;

/**
 * Tracks calls to methods that remove elements from a Collection.
 * Such methods perform operations at the head, middle or tail of the Collection.
 *
 * <p>There are duplicate code between this and {@link AddCallTrack}
 * because the instrumentation doesn't accept injection
 * of classes hierarchy.</p>
 *
 * @author Manoel Campos da Silva Filho
 */
public class RemoveCallTrack {
    protected final CollectionMetric metric;
    private String operation;
    private int calls;
    private int heads;
    private int middles;
    private int tails;
    private int moves;

    /**
     * Instantiates an object.
     *
     * @param operation the description of the collection operation to track
     * @param metric    the metric that keeps all tracking data/objects.
     */
    public RemoveCallTrack(final String operation, final CollectionMetric metric) {
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
        /*TODO:
           The function param is considering T remove(index).
           When the boolean remove(Object) is called, we have no direct way to find out
           the position removed.
           One possible way is to use reflection
           inside the OnEnter advice to find the object position
           and pass that position to OnExig advice.
           But using reflection to access methods on the collection
           causes StackOverflow since the advice calls advised
           methods that fires the advice again. */
        return (int) call.getArg(0);
    }

    /**
     * Checks if the method call operated over the tail of the Collection.
     *
     * @param index the position where the item was removed
     * @return
     */
    protected boolean isLastIndex(final int index) {
        return index == metric.getSize().getPrevious() - 1;
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
     * Gets the number of items moved due to an remove operation.
     * @return
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Checks if the method call operated over the head of the Collection.
     *
     * @param index the position where the item was removed
     * @return
     */
    private boolean isFirstIndex(final int index) {
        return index == 0;
    }

    /**
     * Collect data about the tracked Collection {@link #getOperation() operations}.
     *
     * @param call information about the collection method call
     */
    public void track(final CollectionCall call) {
        incCalls();
        final int index = getIndex(call);
        if (isLastIndex(index))
            this.tails++;
        else if (isFirstIndex(index))
            this.heads++;
        else this.middles++;

        //TODO this is just considering ArrayList operations
        this.moves += metric.getSize().getPrevious() - (index + 1);
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

    @Override
    public String toString() {
        return String.format(
                "%s: %d -> head %d middle %d tail %d moves %d",
                getOperation(), getCalls(), heads, middles, tails, moves);
    }
}
