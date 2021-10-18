package com.manoelcampos.collectionsbuddy;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Collection;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * A Java Agent to trace calls to methods on {@link Collection} classes.
 */
public class CollectionAgent {
    public static void main(String[] args) throws IOException{
        System.out.printf("%nStarting %s%n", CollectionAgent.class.getName());
        final var instrumentation = ByteBuddyAgent.install();
        premain("", instrumentation);
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
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(nameEndsWith("ArrayList"))
                .transform((builder, type, loader, module) -> builder
                        .visit(Advice
                                .to(CollectionAdvices.class)
                                .on(isMethod())
                        ))
                .installOn(inst);
    }
}
