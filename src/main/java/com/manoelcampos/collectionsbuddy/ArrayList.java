package com.manoelcampos.collectionsbuddy;

/**
 * A class with the same name of {@link java.util.ArrayList}
 * that can be used instead of the latter to check if
 * the agent is actually intercepting method calls on
 * a class with such a name, regardless if it's a JDK class or not.
 * @param <T>
 */
public class ArrayList<T extends Object> {
    public boolean add(T e){
        return true;
    }
    public T get(int i){
        return null;
    }
    public int size(){
        return 1;
    }
}
