package org.anima.tinytypes;

public abstract class ByteTinyType {

    public final byte value;

    public ByteTinyType(byte value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return (int) value; //as per implementation of Byte::hashCode(byte) in jdk8.0.45
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ByteTinyType other = (ByteTinyType) obj;
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return String.format("%s#%s", this.getClass().getSimpleName(), value);
    }
}
