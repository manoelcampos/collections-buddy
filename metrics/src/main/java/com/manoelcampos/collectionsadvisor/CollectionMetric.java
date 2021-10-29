package com.manoelcampos.collectionsadvisor;

/**
 * Stores data about operations over a {@link java.util.Collection} object.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionMetric {
    private int calls;
    private int lookups;
    private int clearUps;
    private final AddCallTrack inserts;
    private final RemoveCallTrack removals;
    private final DimensionCallTrack size;

    public CollectionMetric(){
        this.size = new DimensionCallTrack("Resizes");
        this.inserts  = new AddCallTrack("Inserts", this);
        this.removals = new RemoveCallTrack("Removals", this);
    }

    /**
     * Gets the number of times methods were called on a given Collection.
     * @return
     */
    public int getCalls() {
        return calls;
    }

    /**
     * Gets the number of times the size of a Collection has changed.
     * @return
     */
    public DimensionCallTrack getSize() {
        return size;
    }

    /**
     * Gets the number of times the Collection was completely cleared up.
     * @return
     */
    public int getClearUps() {
        return clearUps;
    }

    /**
     * Gets the number of times items were inserted on the Collection.
     * @return
     */
    public AddCallTrack getInserts() {
        return inserts;
    }

    /**
     * Gets the number of times items were removed on the Collection.
     * @return
     */
    public RemoveCallTrack getRemovals() {
        return removals;
    }

    /**
     * Gets the number of times items were accessed at the Collection.
     * @return
     */
    public int getLookups() {
        return lookups;
    }

    /**
     * Track calls to Collection methods and compute metrics.
     * @param call information about the collection method call
     */
    public void track(final CollectionCall call) {
        this.calls++;
        size.track(call);

        if(call.isClear())
            this.clearUps++;
        else if(call.isGet())
            this.lookups++;
        else if(call.isAdd())
            inserts.track(call);
        else if(call.isRemove())
            removals.track(call);
    }

    @Override
    public String toString() {
        return String.format(
            "Calls: %d Lookups: %d Clear Ups: %d | %s | %s | %s",
             calls, lookups, clearUps, size, inserts, removals);
    }
}
