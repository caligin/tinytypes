package tech.anima.tinytypes;

/**
 * Base abstract class for a String-based TinyType. A class extending this will
 * be recognized and processed by the TinyTypes support libraries, as long as
 * it's not abstract and provides a ctor matching the one on this class.
 */
public abstract class StringTinyType {

    public final String value;

    /**
     * Wrapping ctor.
     *
     * @param value the native value to be wrapped.
     * @throws IllegalArgumentException for a null value.
     */
    public StringTinyType(String value) {
        if (value == null) {
            throw new IllegalArgumentException("a StringTinyType cannot have a null value");
        }
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StringTinyType other = (StringTinyType) obj;
        return this.value.equals(other.value);
    }

    /**
     * Get a String representation of this TinyType. Please note that this is
     * not meant to be a "stringification" of the boxed value.
     *
     * @return A type-informative, value-informative representation of this
     * TinyType instance.
     */
    @Override
    public String toString() {
        return String.format("%s#%s", this.getClass().getSimpleName(), value);
    }

}
