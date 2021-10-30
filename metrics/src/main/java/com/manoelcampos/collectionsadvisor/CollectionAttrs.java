package com.manoelcampos.collectionsadvisor;

public class CollectionAttrs {
    private int currentSize;
    private int currentCapacity;
    private final int previousSize;
    private final int previousCapacity;

    public CollectionAttrs(final int previousSize) {
        this(previousSize, 0);
    }

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

    @Override
    public String toString() {
        final var sb = new StringBuilder("Attrs{");
        sb.append("previousSize=").append(previousSize);
        sb.append(", currentSize=").append(currentSize);
        sb.append("previousCapacity=").append(previousCapacity);
        sb.append(", currentCapacity=").append(currentCapacity);
        sb.append('}');
        return sb.toString();
    }
}
