package org.anima.tinytypes.meta;

import org.anima.tinytypes.IntTinyType;

public abstract class IntTinyTypes {

    public static boolean includes(Class<?> candidate) {
        return IntTinyType.class.equals(candidate.getSuperclass());
    }

}
