package org.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.StringTinyType;

public class StringTinyTypes implements MetaTinyType<StringTinyType> {

    public static boolean includes(Class<?> candidate) {
        return StringTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return StringTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(StringTinyType value) {
        return value.value;
    }

    @Override
    public <U extends StringTinyType> U newInstance(Class<U> type, Object value) {
        if (!includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", StringTinyType.class.getSimpleName(), type.getCanonicalName()));
        }
        try {
            return type.getConstructor(String.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public <U extends StringTinyType> U fromString(Class<U> type, String value) {
        return newInstance(type, value);
    }

}
