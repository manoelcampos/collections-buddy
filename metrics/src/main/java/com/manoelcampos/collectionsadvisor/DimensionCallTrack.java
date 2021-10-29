package com.manoelcampos.collectionsadvisor;

/**
 * Tracks calls to a given Collection method that change its dimension,
 * such as size or capacity.
 *
 * @author Manoel Campos da Silva Filho
 */
public class DimensionCallTrack extends AbstractCallTrack {
    private int current;
    private int previous;
    private int increases;
    private int decreases;

    /**
     * Instantiates an object.
     * @param operation the description of the collection operation to track
     */
    public DimensionCallTrack(final String operation) {
        super(operation);
    }

    /** Gets the number of calls that increased the Collection dimension. */
    public int getIncreases() {
        return increases;
    }

    /** Gets the number of calls that decreased the Collection dimension. */
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

    /**
     * Checks if the tracked Collection dimension has changed in the last tracked method call.
     * @return
     */
    public boolean isChanged(){
        return current != previous;
    }

    /**
     * Checks if the tracked Collection dimension has decreased in the last tracked method call.
     * @return
     */
    public boolean isSmaller(){
        return current < previous;
    }

    /**
     * Checks if the tracked Collection dimension has increased in the last tracked method call.
     * @return
     */
    public boolean isLarger(){
        return current > previous;
    }

    /**
     * Gets the current value of the attribute representing
     * the tracked Collection dimension.
     */
    public int getCurrent() {
        return current;
    }

    /**
     * The previous value of the attribute representing the tracked Collection dimension.
     */
    public int getPrevious() {
        return previous;
    }

    /**
     * Sets the current and previous value of the attribute representing
     * the tracked Collection dimension.
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
