package com.manoelcampos.collectionsadvisor;

/**
 * Stores data about operations over a {@link java.util.Collection} object.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionMetric {
    private int calls;
    private int resizes;
    private int capacityIncreases;
    private int capacityDecreases;
    private int clearUps;
    private int inserts;
    private int headInserts;
    private int middleInserts;
    private int tailInserts;
    private int lookups;

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
    public int getResizes() {
        return resizes;
    }

    /**
     * Number of times the capacity of a Collection has increased.
     * @return
     */
    public int getCapacityIncreases() {
        return capacityIncreases;
    }

    /**
     * Number of times the capacity of a Collection has decreased.
     * @return
     */
    public int getCapacityDecreases() {
        return capacityDecreases;
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
    public int getInserts() {
        return inserts;
    }

    /**
     * Number of times items were inserted at the head of the Collection.
     * @return
     */
    public int getHeadInserts() {
        return headInserts;
    }

    /**
     * Number of times items were inserted at the middle of the Collection.
     * @return
     */
    public int getMiddleInserts() {
        return middleInserts;
    }

    /**
     * Number of times items were inserted at the tail of the Collection.
     * @return
     */
    public int getTailInserts() {
        return tailInserts;
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
        trackSize(call);

        if(call.isClear())
            this.clearUps++;
        else if(call.isGet())
            this.lookups++;
        else if(call.isAdd()) {
            trackAdd(call);
        }
    }

    private void trackAdd(final CollectionCall call) {
        this.inserts++;
        if(call.isAddTail())
            this.tailInserts++;
        else if(call.isAddHead())
            this.headInserts++;
        else this.middleInserts++;
    }

    private void trackSize(final CollectionCall call) {
        if(call.isSizeChanged()){
            this.resizes++;
        }

        if(call.isSizeSmaller())
            this.capacityDecreases++;
        else if(call.isSizeLarger())
            this.capacityIncreases++;
    }

    @Override
    public String toString() {
        return String.format(
            "Calls: %d Resizes: %d Growths: %d Reductions: %d Clear Ups: %d Inserts: %d Head Inserts: %d Inner Inserts: %d Tail Inserts: %d Lookups: %d",
             calls, resizes, capacityIncreases, capacityDecreases, clearUps, inserts, headInserts, middleInserts, tailInserts, lookups);
    }
}
