package com.sample;

import com.manoelcampos.collectionsadvisor.CollectionAgent;
import com.manoelcampos.collectionsadvisor.Metrics;

/**
 * A sample to check that method calls on JDK {@link java.util.Collection} classes
 * are being intercepted by the implemented {@link CollectionAgent}.
 * @author Manoel Campos da Silva Filho
 */
public class Sample {
    /**
     * Runs sample code that uses instrumented {@link java.util.Collections}
     * to check if the agent is working.
     */
    public Sample() {
        System.out.printf("%nStarting %s%n", getClass().getName());

        new LinkedListSample();
        new ArrayListRegularSample();
        new ArrayListHeadAddSample();
        new ArrayListAsQueueSample();

        System.out.println();
        Metrics.print();
    }
}
