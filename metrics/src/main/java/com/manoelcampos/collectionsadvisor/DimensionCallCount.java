package com.manoelcampos.collectionsadvisor;

/**
 * Counts the number of calls for a given collection method
 * that change its dimension, such as its size or capacity.
 */
public class DimensionCallCount extends AbstractCallCount{
    private int current;
    private int previous;
    private int increases;
    private int decreases;

    public DimensionCallCount(final String operation) {
        super(operation);
    }

    /** Gets the number of calls that increased some collection dimension. */
    public int getIncreases() {
        return increases;
    }

    /** Gets the number of calls that decreased some collection dimension. */
    public int getDecreases() {
        return decreases;
    }

    @Override
    public void track(final CollectionCall call) {
        if(isChanged())
            incCalls();

        if(isSmaller())
            this.decreases++;
        else if(isLarger())
            this.increases++;
    }

    public boolean isChanged(){
        return current != previous;
    }

    public boolean isSmaller(){
        return current < previous;
    }

    public boolean isLarger(){
        return current > previous;
    }

    /**
     * The current value of the attribute representing
     * some collection dimension (such as size or capacity).
     */
    public int getCurrent() {
        return current;
    }

    /**
     * The previous value of the attribute representing
     * some collection dimension (such as size or capacity).
     */
    public int getPrevious() {
        return previous;
    }

    /**
     * Sets the current and previous value of the attribute representing
     * some collection dimension (such as size or capacity).
     */
    public void setValue(final int previous, final int current) {
        this.previous = previous;
        this.current = current;
    }

    @Override
    public String toString() {
        return String.format("%s: %d -> inc %d dec %d", getOperation(), getCalls(), increases, decreases);
    }
}
