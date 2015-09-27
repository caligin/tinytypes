package org.anima.tinytypes.meta;

import org.anima.tinytypes.ShortTinyType;

public abstract class ShortTinyTypes {

    public static boolean includes(Class<?> candidate) {
        return ShortTinyType.class.equals(candidate.getSuperclass());
    }
}
