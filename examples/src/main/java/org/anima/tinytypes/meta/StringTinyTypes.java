package org.anima.tinytypes.meta;

import org.anima.tinytypes.StringTinyType;

public abstract class StringTinyTypes {

    public static boolean includes(Class<?> candidate) {
        return StringTinyType.class.equals(candidate.getSuperclass());
    }

}
