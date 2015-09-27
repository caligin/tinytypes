package org.anima.tinytypes.meta;

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

}
