package tech.anima.tinytypes;

public abstract class BooleanTinyType {

    public final boolean value;

    public BooleanTinyType(boolean value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value ? 1231 : 1237; //as per implementation of Boolean::hashCode(boolean) in jdk8.0.45
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

    @Override
    public String toString() {
        return String.format("%s#%s", this.getClass().getSimpleName(), value);
    }

}
