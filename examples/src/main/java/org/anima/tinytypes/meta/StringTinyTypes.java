package org.anima.tinytypes.meta;

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

}
