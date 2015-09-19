package org.anima.tinytypes.examples;

import org.anima.tinytypes.BooleanTinyType;
import org.anima.tinytypes.ByteTinyType;
import org.anima.tinytypes.IntTinyType;
import org.anima.tinytypes.LongTinyType;
import org.anima.tinytypes.ShortTinyType;
import org.anima.tinytypes.StringTinyType;

public class Samples {

    public static class Integer extends IntTinyType {

        public Integer(int value) {
            super(value);
        }

    }

    public static class OtherInteger extends IntTinyType {

        public OtherInteger(int value) {
            super(value);
        }

    }

    public static class Byte extends ByteTinyType {

        public Byte(byte value) {
            super(value);
        }

    }

    public static class OtherByte extends ByteTinyType {

        public OtherByte(byte value) {
            super(value);
        }

    }

    public static class Str extends StringTinyType {

        public Str(String value) {
            super(value);
        }

    }

    public static class OtherStr extends StringTinyType {

        public OtherStr(String value) {
            super(value);
        }

    }

    public static class Boolean extends BooleanTinyType {

        public Boolean(boolean value) {
            super(value);
        }

    }

    public static class OtherBoolean extends BooleanTinyType {

        public OtherBoolean(boolean value) {
            super(value);
        }

    }

    public static class Long extends LongTinyType {

        public Long(long value) {
            super(value);
        }

    }

    public static class OtherLong extends LongTinyType {

        public OtherLong(long value) {
            super(value);
        }

    }

    public static class Short extends ShortTinyType {

        public Short(short value) {
            super(value);
        }

    }

    public static class OtherShort extends ShortTinyType {

        public OtherShort(short value) {
            super(value);
        }

    }

}
