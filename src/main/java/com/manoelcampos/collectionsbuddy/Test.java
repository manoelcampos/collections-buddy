package com.manoelcampos.collectionsbuddy;

import java.util.Random;

//Uncomment this to confirm that JDK classes are being instrumented
import java.util.ArrayList;

public class Test {
    private final ArrayList<Integer> arrayList;

    public Test(){
        System.out.printf("%nStarting %s%n", Test.class.getName());

        this.arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i);
        }

        final Random rand = new Random();
        for (int i = 0; i < 200; i++) {
            arrayList.get(rand.nextInt(arrayList.size()));
        }
    }
}
