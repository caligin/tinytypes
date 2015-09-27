package org.anima.tinytypes.meta;

import org.anima.tinytypes.LongTinyType;

public abstract class LongTinyTypes {

    public static boolean includes(Class<?> candidate) {
        return LongTinyType.class.equals(candidate.getSuperclass());
    }

}
