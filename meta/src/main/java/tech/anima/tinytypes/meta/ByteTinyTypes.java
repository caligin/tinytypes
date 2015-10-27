package tech.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import tech.anima.tinytypes.ByteTinyType;

public class ByteTinyTypes implements MetaTinyType<ByteTinyType> {

    public static boolean includes(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(ByteTinyType value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return Byte.toString(value.value);
    }

    @Override
    public <U extends ByteTinyType> U newInstance(Class<U> type, Object value) {
        if (type == null || !includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", ByteTinyType.class.getSimpleName(), type == null ? "null" : type.getCanonicalName()));
        }
        try {
            return type.getConstructor(byte.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public <U extends ByteTinyType> U fromString(Class<U> type, String value) {
        return newInstance(type, Byte.parseByte(value));
    }

}
