package tech.anima.tinytypes.meta;

/**
 * Meta support for a TinyType type.
 *
 * @param <T> the TinyType class type which this Meta is for.
 */
public interface MetaTinyType<T> {

    /**
     * Checks whether this implementation can operate on the given TinyType
     * class type.
     *
     * @param candidate the TInyType type to check for compatibility
     * @return true if the candidate is supported, false otherwise
     */
    boolean isMetaOf(Class<?> candidate);

    /**
     * Returns a String representation of the value wrapped in the given
     * TinyType. The given TinyType must be compatible with this meta.
     *
     * @param value the TinyType to stringify
     * @return a String representation of the native value wrapped in the
     * TinyType
     */
    String stringify(T value);

    /**
     * Creates a new instance of the given TinyType class type, wrapping the
     * given value. The given TinyType class type and the type of value must
     * both be compatible with this meta.
     *
     * @param <U> the TinyType class type to construct
     * @param type the TinyType class type to construct
     * @param value the native value to wrap
     * @return a new instance of U wrapping the given value
     */
    <U extends T> U newInstance(Class<U> type, Object value);

    /**
     * Creates a new instance of the given TinyType class type, wrapping the
     * given value given in string form. The given TinyType class type must be
     * compatible with this meta, the given String value must be parseable as
     * the appropriate native type.
     *
     * @param <U> the TinyType class type to construct
     * @param type the TinyType class type to construct
     * @param value the native value to wrap
     * @return a new instance of U wrapping the given value
     */
    <U extends T> U fromString(Class<U> type, String value);
}
