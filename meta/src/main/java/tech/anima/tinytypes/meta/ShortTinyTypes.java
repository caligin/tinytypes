package tech.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import tech.anima.tinytypes.ShortTinyType;

public class ShortTinyTypes implements MetaTinyType<ShortTinyType> {

    public static boolean includes(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(ShortTinyType value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return Short.toString(value.value);
    }

    @Override
    public <U extends ShortTinyType> U newInstance(Class<U> type, Object value) {
        if (type == null || !includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", ShortTinyType.class.getSimpleName(), type == null ? "null" : type.getCanonicalName()));
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
