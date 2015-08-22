package org.anima.tinytypes;

public abstract class LongTinyType {

    public final long value;

    public LongTinyType(long value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
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

}
