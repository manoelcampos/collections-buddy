package com.manoelcampos.collectionsadvisor;

import java.util.function.Function;

public class AddCallCount extends AbstractPositionCallCount {
    public AddCallCount(final String operation, final CollectionMetric metric) {
        super(operation, metric);
    }

    @Override
    protected int getIndex(CollectionCall call) {
        return call.isSingleArg() ? metric.getSize().getPrevious() : (int) call.getArg(0);
    }

    @Override
    protected boolean isLastIndex(final CollectionCall call) {
        return getIndex(call) == metric.getSize().getCurrent()-1;
    }
}
