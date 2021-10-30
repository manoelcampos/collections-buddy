package com.manoelcampos.collectionsadvisor;

import java.lang.reflect.Field;
import java.util.List;

public final class ReflectionUtils {
    private ReflectionUtils(){/**/}

    public static int getArrayListCapacity(final List<?> collection, final String collectionClass) {
        final var collectionRef = new CollectionReference(collectionClass);
        if (!collectionRef.isArrayList()) {
            return 0;
        }

        try {
            final Field field = collection.getClass().getField("elementData");
            field.setAccessible(true);
            final var internalArray = (Object[]) field.get(collection);
            System.out.println("len: " + internalArray.length);
            return internalArray.length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return -1;
        }
    }
}
