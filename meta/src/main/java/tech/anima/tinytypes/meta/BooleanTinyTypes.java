package tech.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import tech.anima.tinytypes.BooleanTinyType;

public class BooleanTinyTypes implements MetaTinyType<BooleanTinyType> {

    public static boolean includes(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }

        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(BooleanTinyType value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return Boolean.toString(value.value);
    }

    @Override
    public <U extends BooleanTinyType> U newInstance(Class<U> type, Object value) {
        if (type == null || !includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", BooleanTinyType.class.getSimpleName(), type == null ? "null" : type.getCanonicalName()));
        }
        try {
            return type.getConstructor(boolean.class).newInstance(value);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (InstantiationException | NoSuchMethodException | ClassCastException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public <U extends BooleanTinyType> U fromString(Class<U> type, String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return newInstance(type, Boolean.parseBoolean(value));
    }

}
