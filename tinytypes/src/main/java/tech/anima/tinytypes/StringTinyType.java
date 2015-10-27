package tech.anima.tinytypes;

public abstract class StringTinyType {

    public final String value;

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

    @Override
    public String toString() {
        return String.format("%s#%s", this.getClass().getSimpleName(), value);
    }

}
