package com.manoelcampos.collectionsbuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Collection;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.util.LinkedList;

/**
 * A Java Agent to trace calls to methods on {@link Collection} classes.
 * @author Manoel Campos da Silva Filho
 */
public class CollectionAgent {
    public static void main(String[] args) throws UnmodifiableClassException {
        System.out.printf("%nStarting %s%n", CollectionAgent.class.getName());
        final var instrumentation = ByteBuddyAgent.install();
        premain("", instrumentation);
        instrumentation.retransformClasses(LinkedList.class);
        new Test();
    }

    /**
     * Creates the Java Agent to instrument code.
     * @param agentArgs command line parameters for the agent
     * @param inst the ByteBuddy instrumentation instante
     */
    public static void premain(final String agentArgs, final Instrumentation inst) {
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
                    builder.visit(Advice.to(CollectionAdvices.class).on(isMethod()))
                )
                .installOn(inst);
    }
}
