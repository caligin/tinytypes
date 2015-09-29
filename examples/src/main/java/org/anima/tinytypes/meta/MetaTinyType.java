package org.anima.tinytypes.meta;

public interface MetaTinyType<T> {

    boolean isMetaOf(Class<?> candidate);

    String stringify(T value);

    <U extends T> U newInstance(Class<U> type, Object value);
}
