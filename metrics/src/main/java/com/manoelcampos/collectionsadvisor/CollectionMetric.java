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
    private int innerInserts;
    private int tailInserts;
    private int lookups;

    /**
     * Number of times methods were called on the Collection.
     * @return
     */
    public int getCalls() {
        return calls;
    }

    public void incCalls() {
        this.calls++;
    }

    /**
     * Number of times the size of a Collection has changed.
     * @return
     */
    public int getResizes() {
        return resizes;
    }

    /**
     * Indicates the collection size has changed one more time.
     * @return
     */
    public void incResizes() {
        this.resizes++;
    }

    /**
     * Number of times the capacity of a Collection has increased.
     * @return
     */
    public int getCapacityIncreases() {
        return capacityIncreases;
    }

    /**
     * Indicates the collection capacity has increased one more time.
     * @return
     */
    public void incCapacityIncreases() {
        this.capacityIncreases++;
    }

    /**
     * Number of times the capacity of a Collection has decreased.
     * @return
     */
    public int getCapacityDecreases() {
        return capacityDecreases;
    }

    /**
     * Indicates the collection capacity has decreased one more time.
     * @return
     */
    public void incCapacityDecreases() {
        this.capacityDecreases++;
    }

    /**
     * Number of times the Collection was completely cleared up.
     * @return
     */
    public int getClearUps() {
        return clearUps;
    }

    /**
     * Indicates the collection has beeen cleared up one more time.
     * @return
     */
    public void incClearUps() {
        this.clearUps++;
    }

    /**
     * Number of times items were inserted on the Collection.
     * @return
     */
    public int getInserts() {
        return inserts;
    }

    public void incInserts() {
        this.inserts++;
    }

    /**
     * Number of times items were inserted at the head of the Collection.
     * @return
     */
    public int getHeadInserts() {
        return headInserts;
    }

    public void incHeadInserts() {
        this.headInserts++;
    }

    /**
     * Number of times items were inserted at the middle of the Collection.
     * @return
     */
    public int getInnerInserts() {
        return innerInserts;
    }

    public void incInnerInserts() {
        this.innerInserts++;
    }

    /**
     * Number of times items were inserted at the tail of the Collection.
     * @return
     */
    public int getTailInserts() {
        return tailInserts;
    }

    public void incTailInserts() {
        this.tailInserts++;
    }

    /**
     * Number of times items were accessed at the Collection.
     * @return
     */
    public int getLookups() {
        return lookups;
    }

    public void incLookups() {
        this.lookups++;
    }

    @Override
    public String toString() {
        return String.format(
            "Calls: %d Resizes: %d Growths: %d Reductions: %d Clear Ups: %d Inserts: %d Head Inserts: %d Inner Inserts: %d Tail Inserts: %d Lookups: %d",
             calls, resizes, capacityIncreases, capacityDecreases, clearUps, inserts, headInserts, innerInserts, tailInserts, lookups);
    }
}
