package com.manoelcampos.dsagent;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;
import net.bytebuddy.dynamic.ClassFileLocator.ForClassLoader;
import net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation;
import net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.util.Map;
import java.util.Random;

import java.util.ArrayList;

public class CollectionAgent {
    public static void premain(final String agentArgs, final Instrumentation inst) throws IOException{
        final File temp = Files.createTempDirectory("temp").toFile();

        final var typesMap = Map.of(new ForLoadedType(CollectionAdvices.class), ForClassLoader.read(CollectionAdvices.class));
        UsingInstrumentation
                .of(temp, Target.BOOTSTRAP, inst)
                .inject(typesMap);

        new AgentBuilder.Default()
                .ignore(ElementMatchers.nameStartsWith("net.bytebuddy."))
                .with(new AgentBuilder.InjectionStrategy.UsingInstrumentation(inst, temp))
                .disableClassFormatChanges()
                .type(ElementMatchers.nameEndsWith(".ArrayList"))
                .transform((builder, typeDescription, classLoader, module) ->
                        builder.visit(Advice.to(CollectionAdvices.class)
                                            .on(ElementMatchers.isMethod())))
                .installOn(inst);
    }

    public static void main(String[] args) throws IOException{
        final Instrumentation instrumentation = ByteBuddyAgent.install();
        premain("", instrumentation);

        System.out.println("Starting " + CollectionAgent.class.getName());
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
