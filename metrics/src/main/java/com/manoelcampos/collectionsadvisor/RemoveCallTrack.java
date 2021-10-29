package com.manoelcampos.collectionsadvisor;

/**
 * Tracks calls to methods that remove elements from a Collection.
 * Such methods perform operations at the head, middle or tail of the Collection.
 *
 * @author Manoel Campos da Silva Filho
 */
public class RemoveCallTrack extends AbstractPositionCallTrack {
    /**
     * Instantiates an object.
     * @param operation the description of the collection operation to track
     * @param metric the metric that keeps all tracking data/objects.
     */
    public RemoveCallTrack(final String operation, final CollectionMetric metric) {
        super(operation, metric);
    }

    @Override
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
        return (int)call.getArg(0);
    }

    @Override
    protected boolean isLastIndex(final CollectionCall call) {
        return getIndex(call) == metric.getSize().getPrevious()-1;
    }
}
