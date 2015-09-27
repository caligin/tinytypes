package org.anima.tinytypes.meta;

import org.anima.tinytypes.BooleanTinyType;

public class BooleanTinyTypes implements MetaTinyType<BooleanTinyType> {

    public static boolean includes(Class<?> candidate) {
        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public boolean isMetaOf(Class<?> candidate) {
        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

    @Override
    public String stringify(BooleanTinyType value) {
        return Boolean.toString(value.value);
    }

}
