package org.anima.tinytypes;

public abstract class IntTinyType {

    public final int value;

    public IntTinyType(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntTinyType other = (IntTinyType) obj;
        return this.value == other.value;
    }

}
