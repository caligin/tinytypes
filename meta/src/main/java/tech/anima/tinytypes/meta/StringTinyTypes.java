package tech.anima.tinytypes.meta;

import java.lang.reflect.InvocationTargetException;
import tech.anima.tinytypes.StringTinyType;

public class StringTinyTypes implements MetaTinyType<StringTinyType> {

    public static boolean includes(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return StringTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        if (candidate == null) {
            return false;
        }
        return StringTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(StringTinyType value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        return value.value;
    }

    @Override
    public <U extends StringTinyType> U newInstance(Class<U> type, Object value) {
        if (value == null) {
            throw new IllegalArgumentException("value must be not null");
        }
        if (type == null || !includes(type)) {
            throw new IllegalArgumentException(String.format("Not a %s: %s", StringTinyType.class.getSimpleName(), type == null ? "null" : type.getCanonicalName()));
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
