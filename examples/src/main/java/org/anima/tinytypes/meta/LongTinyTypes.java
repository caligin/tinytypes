package org.anima.tinytypes.meta;

import org.anima.tinytypes.LongTinyType;

public class LongTinyTypes implements MetaTinyType<LongTinyType> {

    public static boolean includes(Class<?> candidate) {
        return LongTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return LongTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(LongTinyType value) {
        return Long.toString(value.value);
    }

}
