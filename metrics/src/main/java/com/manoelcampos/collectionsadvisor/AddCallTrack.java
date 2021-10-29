package com.manoelcampos.collectionsadvisor;

/**
 * Tracks calls to methods that add elements to a Collection.
 * Such methods perform operations at the head, middle or tail of the Collection.
 *
 * @author Manoel Campos da Silva Filho
 */
public class AddCallTrack extends AbstractPositionCallTrack {
    /**
     * Instantiates an object.
     * @param operation the description of the collection operation to track
     * @param metric the metric that keeps all tracking data/objects.
     */
    public AddCallTrack(final String operation, final CollectionMetric metric) {
        super(operation, metric);
    }

    @Override
    protected int getIndex(final CollectionCall call) {
        return call.isSingleArg() ? metric.getSize().getPrevious() : (int) call.getArg(0);
    }

    @Override
    protected boolean isLastIndex(final CollectionCall call) {
        return getIndex(call) == metric.getSize().getCurrent()-1;
    }
}
