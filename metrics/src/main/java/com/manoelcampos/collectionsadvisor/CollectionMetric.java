package com.manoelcampos.collectionsadvisor;

/**
 * Stores data about operations over a {@link java.util.Collection} object.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionMetric {
    private int calls;
    private int lookups;
    private int clearUps;
    private AddCallCount inserts;
    private RemoveCallCount removals;
    private DimensionCallCount size;

    public CollectionMetric(){
        this.size = new DimensionCallCount("Resizes");
        this.inserts  = new AddCallCount("Inserts", this);
        this.removals = new RemoveCallCount("Removals", this);
    }

    /**
     * Number of times methods were called on the Collection.
     * @return
     */
    public int getCalls() {
        return calls;
    }

    /**
     * Number of times the size of a Collection has changed.
     * @return
     */
    public DimensionCallCount getSize() {
        return size;
    }

    /**
     * Number of times the Collection was completely cleared up.
     * @return
     */
    public int getClearUps() {
        return clearUps;
    }

    /**
     * Number of times items were inserted on the Collection.
     * @return
     */
    public AbstractPositionCallCount getInserts() {
        return inserts;
    }

    /**
     * Number of times items were removed on the Collection.
     * @return
     */
    public AbstractPositionCallCount getRemovals() {
        return removals;
    }

    /**
     * Number of times items were accessed at the Collection.
     * @return
     */
    public int getLookups() {
        return lookups;
    }

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
