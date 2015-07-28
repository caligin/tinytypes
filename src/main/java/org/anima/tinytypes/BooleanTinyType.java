package org.anima.tinytypes;

public abstract class BooleanTinyType {

    public final boolean value;

    public BooleanTinyType(boolean value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BooleanTinyType other = (BooleanTinyType) obj;
        return this.value == other.value;
    }

}
