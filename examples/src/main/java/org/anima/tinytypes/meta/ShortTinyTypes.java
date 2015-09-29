package org.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.ShortTinyType;

public class ShortTinyTypes implements MetaTinyType<ShortTinyType> {

    public static boolean includes(Class<?> candidate) {
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(ShortTinyType value) {
        return Short.toString(value.value);
    }

    @Override
    public <U extends ShortTinyType> U newInstance(Class<U> type, Object value) {
        if (!includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", ShortTinyType.class.getSimpleName(), type.getCanonicalName()));
        }
        try {
            return type.getConstructor(short.class).newInstance((short) value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public <U extends ShortTinyType> U fromString(Class<U> type, String value) {
        return newInstance(type, Short.parseShort(value));
    }

}
