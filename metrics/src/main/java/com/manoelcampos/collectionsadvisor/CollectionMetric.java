package com.manoelcampos.collectionsadvisor;

/**
 * Stores data about operations over a {@link java.util.Collection} object.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionMetric {
    private int calls;
    private int resizes;
    private int sizeIncreases;
    private int sizeDecreases;
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
     * Number of times the size of a Collection has increased.
     * @return
     */
    public int getSizeIncreases() {
        return sizeIncreases;
    }

    /**
     * Number of times the size of a Collection has decreased.
     * @return
     */
    public int getSizeDecreases() {
        return sizeDecreases;
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
            this.sizeDecreases++;
        else if(call.isSizeLarger())
            this.sizeIncreases++;
    }

    @Override
    public String toString() {
        return String.format(
            "Calls: %d Lookups: %d Clear Ups: %d Resizes: %d -> %d inc %d dec | Capacity: %d inc %d dec | Inserts: %d head %d inner %d tail %d",
             calls, lookups, clearUps, resizes, sizeIncreases, sizeDecreases,
             capacityDecreases, capacityDecreases,
             inserts, headInserts, middleInserts, tailInserts);
    }
}
