package tech.anima.tinytypes;

/**
 * Base abstract class for a long-based TinyType. A class extending this will be
 * recognized and processed by the TinyTypes support libraries, as long as it's
 * not abstract and provides a ctor matching the one on this class.
 */
public abstract class LongTinyType {

    public final long value;

    /**
     * Wrapping ctor.
     *
     * @param value the native value to be wrapped.
     */
    public LongTinyType(long value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32)); //as per implementation of Long::hashCode(long) in jdk8.0.45
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LongTinyType other = (LongTinyType) obj;
        return this.value == other.value;
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
