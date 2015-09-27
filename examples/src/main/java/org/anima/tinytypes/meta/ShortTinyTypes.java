package org.anima.tinytypes.meta;

import org.anima.tinytypes.ShortTinyType;

public class ShortTinyTypes implements MetaTinyType<ShortTinyType> {

    public static boolean includes(Class<?> candidate) {
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(ShortTinyType value) {
        return Short.toString(value.value);
    }
}
