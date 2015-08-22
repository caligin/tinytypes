package org.anima.tinytypes;

public abstract class ShortTinyType {

    public final short value;

    public ShortTinyType(short value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Short.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShortTinyType other = (ShortTinyType) obj;
        return this.value == other.value;
    }

}
