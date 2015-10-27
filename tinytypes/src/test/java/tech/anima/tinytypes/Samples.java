package tech.anima.tinytypes;

public class Samples {

    public static class Integer extends IntTinyType {

        public Integer(int value) {
            super(value);
        }

        public static Integer of(int value) {
            return new Integer(value);
        }

    }

    public static class OtherInteger extends IntTinyType {

        public OtherInteger(int value) {
            super(value);
        }

        public static OtherInteger of(int value) {
            return new OtherInteger(value);
        }

    }

    public static class Byte extends ByteTinyType {

        public Byte(byte value) {
            super(value);
        }

        public static Byte of(byte value) {
            return new Byte(value);
        }

    }

    public static class OtherByte extends ByteTinyType {

        public OtherByte(byte value) {
            super(value);
        }

        public static OtherByte of(byte value) {
            return new OtherByte(value);
        }

    }

    public static class Str extends StringTinyType {

        public Str(String value) {
            super(value);
        }

        public static Str of(String value) {
            return new Str(value);
        }

    }

    public static class OtherStr extends StringTinyType {

        public OtherStr(String value) {
            super(value);
        }

        public static OtherStr of(String value) {
            return new OtherStr(value);
        }

    }

    public static class Boolean extends BooleanTinyType {

        public Boolean(boolean value) {
            super(value);
        }

        public static Boolean of(boolean value) {
            return new Boolean(value);
        }

    }

    public static class OtherBoolean extends BooleanTinyType {

        public OtherBoolean(boolean value) {
            super(value);
        }

        public static OtherBoolean of(boolean value) {
            return new OtherBoolean(value);
        }

    }

    public static class Long extends LongTinyType {

        public Long(long value) {
            super(value);
        }

        public static Long of(long value) {
            return new Long(value);
        }

    }

    public static class OtherLong extends LongTinyType {

        public OtherLong(long value) {
            super(value);
        }

        public static OtherLong of(long value) {
            return new OtherLong(value);
        }

    }

    public static class Short extends ShortTinyType {

        public Short(short value) {
            super(value);
        }

        public static Short of(short value) {
            return new Short(value);
        }

    }

    public static class OtherShort extends ShortTinyType {

        public OtherShort(short value) {
            super(value);
        }

        public static OtherShort of(short value) {
            return new OtherShort(value);
        }

    }

}
