package com.manoelcampos.collectionsadvisor;

import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;

/**
 * The entry point for starting and configuring the {@link CollectionAgent}
 * @author Manoel Campos da Silva Filho
 */
public class AgentEntry {
    private static final String COLLECTIONS_ADVISOR_JAR = "collections-advisor-metrics.jar";

    public static void main(final String[] args) {
        System.out.printf("%nStarting %s%n", AgentEntry.class.getName());
        final var instrumentation = ByteBuddyAgent.install();
        premain("", instrumentation);
    }

    /**
     * Starts up the creation of the Java Agent
     * @param agentArgs command line parameter for the agent
     *                  to indicate the package in which to track calls to java.util.Collection objects.
     *                  You can pass such a parameter for the agent by executing your app for instance as:
     *                  <p>{@code java -javaagent:collections-advisor-agent.jar=com.manoelcampos -jar sample-app.jar}</p>
     *                  This way, only Collections inside classes from package com.manoelcampos will be tracked.
     * @param inst the ByteBuddy instrumentation instante
     */
    public static void premain(final String agentArgs, final Instrumentation inst) {
        loadCommonAdvisorJarOnBootstrapClassloader(inst);
        CollectionAgent.premain(agentArgs, inst);
    }

    /**
     * Uses the bootstrap classloader to load a common jar with classes that will be shared by the agent and the
     * user app. This way, the classes in such a jar will be visible by the agent and app.
     * For more details, check the metrics project README.
     *
     * <p>For it to work, this class must have only ByteBuddy code to load
     * the jar.</p>
     *
     * @param inst the ByteBuddy instrumentation instante
     */
    static void loadCommonAdvisorJarOnBootstrapClassloader(final Instrumentation inst) {
        final String target = "metrics/target/" + COLLECTIONS_ADVISOR_JAR;
        final String[] paths = {COLLECTIONS_ADVISOR_JAR, target, "../" + target};
        UncheckedIOException ex = null;
        for (final String path : paths) {
            try {
                inst.appendToBootstrapClassLoaderSearch(new JarFile(path));
                return;
            } catch (final IOException e) {
                ex = new UncheckedIOException(e);
            }
        }

        throw ex;
    }
}
