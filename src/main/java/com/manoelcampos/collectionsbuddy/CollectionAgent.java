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
    public static void premain(final String agentArgs, final Instrumentation inst) throws IOException{
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

    public static void main(String[] args) throws IOException{
        System.out.printf("%nStarting %s%n", CollectionAgent.class.getName());
        final Instrumentation instrumentation = ByteBuddyAgent.install();
        premain("", instrumentation);
        new Test();
    }
}
