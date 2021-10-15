package com.manoelcampos.dsagent;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.Random;

//Uncomment this to confirm that JDK classes are being instrumented
import java.util.ArrayList;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * A Java Agent to trace calls to methods on {@link Collection} classes.
 * @see <a href="https://www.sitepoint.com/fixing-bugs-in-running-java-code-with-dynamic-attach/">Fixing Bugs in Running Java Code with Dynamic Attach</a>
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
