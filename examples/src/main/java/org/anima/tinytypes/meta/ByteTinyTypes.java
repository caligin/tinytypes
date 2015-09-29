package org.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.ByteTinyType;

public class ByteTinyTypes implements MetaTinyType<ByteTinyType> {

    public static boolean includes(Class<?> candidate) {
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(ByteTinyType value) {
        return Byte.toString(value.value);
    }

    @Override
    public <U extends ByteTinyType> U newInstance(Class<U> type, Object value) {
        if (!includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", ByteTinyType.class.getSimpleName(), type.getCanonicalName()));
        }
        try {
            return type.getConstructor(byte.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
