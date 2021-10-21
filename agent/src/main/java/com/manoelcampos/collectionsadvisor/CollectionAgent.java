package com.manoelcampos.collectionsadvisor;

import com.manoelcampos.collectionsadvisor.sample.Sample;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Collection;
import java.util.LinkedList;

import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.matcher.ElementMatchers.isMethod;

/**
 * A Java Agent to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionAgent {
    /**
     * Creates the Java Agent to instrument code.
     * @param agentArgs check {@link AgentEntry#premain(String, Instrumentation)} for details
     * @param inst the ByteBuddy instrumentation instante
     */
    public static void premain(final String agentArgs, final Instrumentation inst) {
        parseAgentParams(agentArgs);
        buildAgent(inst);

        try {
            inst.retransformClasses(LinkedList.class);
        } catch (final UnmodifiableClassException e) {
            throw new RuntimeException(e);
        }

        new Sample();
    }

    private static void buildAgent(final Instrumentation inst) {
        new AgentBuilder.Default()
                // by default, JVM classes are not instrumented
                .ignore(none())
                // Ignore Byte Buddy and JDK classes we are not interested in
                .ignore(
                        nameStartsWith("net.bytebuddy.")
                                .or(nameStartsWith("jdk.internal.reflect."))
                                .or(nameStartsWith("java.lang.invoke."))
                                .or(nameStartsWith("com.sun.proxy.")))
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                // Make sure we see helpful logs
                .with(AgentBuilder.RedefinitionStrategy.Listener.StreamWriting.toSystemError())
                .with(AgentBuilder.Listener.StreamWriting.toSystemError().withTransformationsOnly())
                .with(AgentBuilder.InstallationListener.StreamWriting.toSystemError())
                .type(nameEndsWith("LinkedList"))
                .transform((builder, type, loader, module) ->
                    builder.visit(Advice.to(CollectionAdvice.class).on(isMethod()))
                )
                .installOn(inst);
    }

    private static void parseAgentParams(final String agentArgs) {
        if(agentArgs != null && !agentArgs.isBlank()){
            Metrics.setTracedPackageName(agentArgs);
        }

        System.out.printf("%nTracking java.util.Collection calls from package %s%n%n", Metrics.getTracedPackageName());
    }

}
