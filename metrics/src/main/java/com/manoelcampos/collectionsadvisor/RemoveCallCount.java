package com.manoelcampos.collectionsadvisor;

public class RemoveCallCount extends AbstractPositionCallCount {
    public RemoveCallCount(final String operation, final CollectionMetric metric) {
        super(operation, metric);
    }

    @Override
    protected int getIndex(CollectionCall call) {
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
