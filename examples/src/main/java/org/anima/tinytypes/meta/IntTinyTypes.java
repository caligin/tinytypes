package org.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.IntTinyType;

public class IntTinyTypes implements MetaTinyType<IntTinyType> {

    public static boolean includes(Class<?> candidate) {
        return IntTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return IntTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(IntTinyType value) {
        return Integer.toString(value.value);
    }

    @Override
    public <U extends IntTinyType> U newInstance(Class<U> type, Object value) {
        if (!includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", IntTinyType.class.getSimpleName(), type.getCanonicalName()));
        }
        try {
            return type.getConstructor(int.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
