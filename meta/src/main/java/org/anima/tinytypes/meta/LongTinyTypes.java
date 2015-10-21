package org.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import org.anima.tinytypes.LongTinyType;

public class LongTinyTypes implements MetaTinyType<LongTinyType> {

    public static boolean includes(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return LongTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return LongTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(LongTinyType value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return Long.toString(value.value);
    }

    @Override
    public <U extends LongTinyType> U newInstance(Class<U> type, Object value) {
        if (type == null || !includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", LongTinyType.class.getSimpleName(), type == null ? "null" : type.getCanonicalName()));
        }
        try {
            return type.getConstructor(long.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public <U extends LongTinyType> U fromString(Class<U> type, String value) {
        return newInstance(type, Long.parseLong(value));
    }

}
