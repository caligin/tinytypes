package org.anima.tinytypes.meta;

import org.anima.tinytypes.ByteTinyType;

public class ByteTinyTypes implements MetaTinyType<ByteTinyType> {

    public static boolean includes(Class<?> candidate) {
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(ByteTinyType value) {
        return Byte.toString(value.value);
    }

}
