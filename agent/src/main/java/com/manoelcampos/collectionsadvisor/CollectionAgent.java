package com.manoelcampos.collectionsadvisor;

import com.sample.Sample;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static net.bytebuddy.matcher.ElementMatchers.*;

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
            inst.retransformClasses(LinkedList.class, ArrayList.class);
        } catch (final UnmodifiableClassException e) {
            throw new RuntimeException(e);
        }

        new Sample();
    }

    private static void buildAgent(final Instrumentation inst) {
        final var agentBuilder = new AgentBuilder.Default()
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
                .with(AgentBuilder.InstallationListener.StreamWriting.toSystemError());

        agentBuilder
                .type(isSubClass(ArrayList.class))
                .transform((builder, type, loader, module) -> builderVisit(builder, ArrayListAdvice.class))
                .type(isSubClass(LinkedList.class))
                .transform((builder, type, loader, module) -> builderVisit(builder, LinkedListAdvice.class))
                .installOn(inst);
    }

    private static DynamicType.Builder<?> builderVisit(final DynamicType.Builder<?> builder, final Class<?> adviceClass) {
        final var advice = Advice.to(adviceClass);
        final var visitor = advice.on(isMethod().and(isPublic()).and(not(isConstructor())).and(not(isStatic())));
        return builder.visit(visitor);
    }

    private static ElementMatcher.Junction<TypeDescription> isSubClass(final Class<?> collectionClass){
        return isSubTypeOf(collectionClass).and(not(isAbstract())).and(not(isAbstract()));
    }

    private static void parseAgentParams(final String agentArgs) {
        if(agentArgs != null && !agentArgs.isBlank()){
            Metrics.setTracedPackageName(agentArgs);
        }

        System.out.printf("%nTracking java.util.Collection calls from package %s%n%n", Metrics.getTracedPackageName());
    }

}
