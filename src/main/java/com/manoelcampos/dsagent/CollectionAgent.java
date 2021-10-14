package com.manoelcampos.dsagent;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Random;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class CollectionAgent {
    public static void premain(final String agentArgs, final Instrumentation inst) throws IOException{
        new AgentBuilder.Default()
                // by default, JVM classes are not instrumented
                .ignore(nameStartsWith("net.bytebuddy."))
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
        System.out.println("Starting " + CollectionAgent.class.getName());

        final Instrumentation instrumentation = ByteBuddyAgent.install();
        premain("", instrumentation);

        final  var arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i);
        }

        final Random rand = new Random();
        for (int i = 0; i < 200; i++) {
            arrayList.get(rand.nextInt(arrayList.size()));
        }
    }
}
