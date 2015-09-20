package org.anima.tinytypes;

public abstract class LongTinyType {

    public final long value;

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

    @Override
    public String toString() {
        return String.format("%s#%s", this.getClass().getSimpleName(), value);
    }

}
