package com.manoelcampos.collectionsadvisor;

public class CollectionAttrs {
    private int currentSize;
    private int currentCapacity;
    private final int previousSize;
    private final int previousCapacity;

    public CollectionAttrs(final int previousSize, final int previousCapacity) {
        this.previousSize = previousSize;
        this.previousCapacity = previousCapacity;
    }

    public int getPreviousSize() {
        return previousSize;
    }

    public int getPreviousCapacity() {
        return previousCapacity;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(final int currentSize) {
        this.currentSize = currentSize;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(final int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
}
