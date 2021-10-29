package com.manoelcampos.collectionsadvisor;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a reference to a {@link Collection} object
 * where its method calls are being tracked.
 *
 * @author Manoel Campos da Silva Filho
 * @see Metrics
 */
public class CollectionReference {
    private final String callerPackage;
    private final String callerSimpleClassName;
    private final String collectionClass;

    /**
     * Instantiates a CollectionReference object.
     * @param callerClass the class containing the {@link Collection} object where its mentioned method was called.
     * @param collectionClass name of the {@link Collection} class where one of its methods was called
     */
    public CollectionReference(final Class<?> callerClass, final String collectionClass) {
        Objects.requireNonNull(callerClass);
        this.callerPackage = callerClass.getPackageName();
        this.callerSimpleClassName = callerClass.getSimpleName();
        this.collectionClass = Objects.requireNonNull(collectionClass);
    }

    /**
     * Checks if the {@link Collection} object where one of its methods was called
     * is <b>not</b> inside a package that the user wants to trace calls.
     * @return
     */
    public boolean isNotInsideTracedPackage(){
        return !callerPackage.startsWith(Metrics.getTracedPackageName());
    }

    /**
     * Gets the name of the {@link Collection} class where one of its methods was called
     * @return
     */
    public String getCollectionClass() {
        return collectionClass;
    }

    /**
     * Gets the simple name of package containing the {@link Collection}
     * object where one of its methods was called.
     * @return the package name
     * @see #getCallerSimpleClassName()
     */
    public String getCallerPackage() {
        return callerPackage;
    }

    /**
     * Gets the qualified name of class containing the {@link Collection}
     * object where one of its methods was called.
     * @return the qualified class name (package + class name)
     * @see #getCallerSimpleClassName()
     */
    public String getCallerClassName() {
        return String.format("%s.%s", callerPackage, callerSimpleClassName);
    }

    /**
     * Gets the simple name of class containing the {@link Collection}
     * object where one of its methods was called.
     * @return the simple class name (without package name)
     * @see #getCallerClassName()
     */
    public String getCallerSimpleClassName() {
        return callerSimpleClassName;
    }

    @Override
    public String toString() {
        return String.format("%s from %s", collectionClass, getCallerClassName());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final var that = (CollectionReference) o;

        if (!callerPackage.equals(that.callerPackage)) return false;
        if (!callerSimpleClassName.equals(that.callerSimpleClassName)) return false;
        return collectionClass.equals(that.collectionClass);
    }

    @Override
    public int hashCode() {
        int result = callerPackage.hashCode();
        result = 31 * result + callerSimpleClassName.hashCode();
        result = 31 * result + collectionClass.hashCode();
        return result;
    }

    public boolean isArrayList(){
        return "java.util.ArrayList".equals(callerSimpleClassName);
    }

    public boolean isList(){
        return callerSimpleClassName.matches("java\\.util\\.+List");
    }
}
