package tech.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import tech.anima.tinytypes.IntTinyType;

public class IntTinyTypes implements MetaTinyType<IntTinyType> {

    public static boolean includes(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return IntTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return IntTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(IntTinyType value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return Integer.toString(value.value);
    }

    @Override
    public <U extends IntTinyType> U newInstance(Class<U> type, Object value) {
        if (type == null || !includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", IntTinyType.class.getSimpleName(), type == null ? "null" : type.getCanonicalName()));
        }
        try {
            return type.getConstructor(int.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public <U extends IntTinyType> U fromString(Class<U> type, String value) {
        return newInstance(type, Integer.parseInt(value));
    }

}
