package com.manoelcampos.collectionsadvisor;

import java.util.Collection;
import java.util.Objects;

/**
 * Identifies a call to a method on a JDK {@link Collection} class.
 * @author Manoel Campos da Silva Filho
 * @see Metrics
 */
public class MethodReference {
    private final String callerPackage;
    private final String callerSimpleClassName;
    private final String methodSignature;

    /**
     * Instantiates a method reference.
     * @param callerClass the class containing the {@link Collection} object where its mentioned method was called.
     * @param methodSignature a String signature of the {@link Collection} method called
     */
    public MethodReference(final Class<?> callerClass, final String methodSignature){
        Objects.requireNonNull(callerClass);
        this.callerPackage = callerClass.getPackageName();
        this.callerSimpleClassName = callerClass.getSimpleName();
        this.methodSignature = Objects.requireNonNull(methodSignature);
    }

    /**
     * Checks if the {@link Collection} object where a method was called
     * is <b>not</b> inside a package that the user wants to trace calls.
     * @return
     */
    public boolean isNotInsideTracedPackage(){
        return !callerPackage.startsWith(Metrics.getTracedPackageName());
    }

    /**
     * Gets a String signature of the {@link Collection} method called.
     * @return
     */
    public String getMethodSignature() {
        return methodSignature;
    }

    /**
     * Gets the simple name of package containing the {@link Collection}
     * object where its mentioned method was called.
     * @return the package name
     * @see #getCallerSimpleClassName()
     */
    public String getCallerPackage() {
        return callerPackage;
    }

    /**
     * Gets the qualified name of class containing the {@link Collection}
     * object where its mentioned method was called.
     * @return the qualified class name (package + class name)
     * @see #getCallerSimpleClassName()
     */
    public String getCallerClassName() {
        return String.format("%s.%s", callerPackage, callerSimpleClassName);
    }

    /**
     * Gets the simple name of class containing the {@link Collection}
     * object where its mentioned method was called.
     * @return the simple class name (without package name)
     * @see #getCallerClassName()
     */
    public String getCallerSimpleClassName() {
        return callerSimpleClassName;
    }

    @Override
    public String toString() {
        return String.format("%s from %s", methodSignature, getCallerClassName());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final var that = (MethodReference) o;

        if (!getCallerPackage().equals(that.getCallerPackage())) return false;
        if (!getCallerSimpleClassName().equals(that.getCallerSimpleClassName())) return false;
        return getMethodSignature().equals(that.getMethodSignature());
    }

    @Override
    public int hashCode() {
        int result = getCallerPackage().hashCode();
        result = 31 * result + getCallerSimpleClassName().hashCode();
        result = 31 * result + getMethodSignature().hashCode();
        return result;
    }
}
