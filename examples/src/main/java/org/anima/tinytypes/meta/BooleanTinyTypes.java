package org.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.BooleanTinyType;

public class BooleanTinyTypes implements MetaTinyType<BooleanTinyType> {

    public static boolean includes(Class<?> candidate) {
        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(BooleanTinyType value) {
        return Boolean.toString(value.value);
    }

    @Override
    public <U extends BooleanTinyType> U newInstance(Class<U> type, Object value) {
        if (!includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", BooleanTinyType.class.getSimpleName(), type.getCanonicalName()));
        }
        try {
            return type.getConstructor(boolean.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
