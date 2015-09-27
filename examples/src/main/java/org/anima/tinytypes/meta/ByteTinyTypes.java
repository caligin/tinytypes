package org.anima.tinytypes.meta;

import org.anima.tinytypes.ByteTinyType;

public abstract class ByteTinyTypes {

    public static boolean includes(Class<?> candidate) {
        return ByteTinyType.class.equals(candidate.getSuperclass());
    }

}
