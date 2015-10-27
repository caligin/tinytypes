package tech.anima.tinytypes;

public abstract class IntTinyType {

    public final int value;

    public IntTinyType(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value;  //as per implementation of Integer::hashCode(int) in jdk8.0.45
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

    @Override
    public String toString() {
        return String.format("%s#%s", this.getClass().getSimpleName(), value);
    }

}
