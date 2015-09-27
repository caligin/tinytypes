package org.anima.tinytypes.meta;

import org.anima.tinytypes.BooleanTinyType;

public abstract class BooleanTinyTypes {

    public static boolean includes(Class<?> candidate) {
        return BooleanTinyType.class.equals(candidate.getSuperclass());
    }

}
