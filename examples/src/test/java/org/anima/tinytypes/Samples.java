package org.anima.tinytypes;

public class Samples {

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

    public static class StrIndirectAncestor extends Samples.Str {

        public StrIndirectAncestor(String value) {
            super(value);
        }
    }

    public static abstract class AbstractStr extends StringTinyType {

        public AbstractStr(String value) {
            super(value);
        }
    }

    public static class NoOneArgCtorStr extends StringTinyType {

        public NoOneArgCtorStr() {
            super("1");
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

    public static class BooleanIndirectAncestor extends Samples.Boolean {

        public BooleanIndirectAncestor(boolean value) {
            super(value);
        }
    }

    public static abstract class AbstractBoolean extends BooleanTinyType {

        public AbstractBoolean(boolean value) {
            super(value);
        }
    }

    public static class NoOneArgCtorBoolean extends BooleanTinyType {

        public NoOneArgCtorBoolean() {
            super((boolean) true);
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

    public static class ByteIndirectAncestor extends Samples.Byte {

        public ByteIndirectAncestor(byte value) {
            super(value);
        }
    }

    public static abstract class AbstractByte extends ByteTinyType {

        public AbstractByte(byte value) {
            super(value);
        }
    }

    public static class NoOneArgCtorByte extends ByteTinyType {

        public NoOneArgCtorByte() {
            super((byte) 1);
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

    public static class ShortIndirectAncestor extends Samples.Short {

        public ShortIndirectAncestor(short value) {
            super(value);
        }
    }

    public static abstract class AbstractShort extends ShortTinyType {

        public AbstractShort(short value) {
            super(value);
        }
    }

    public static class NoOneArgCtorShort extends ShortTinyType {

        public NoOneArgCtorShort() {
            super((short) 1);
        }
    }

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

    public static class IntIndirectAncestor extends Samples.Integer {

        public IntIndirectAncestor(int value) {
            super(value);
        }
    }

    public static abstract class AbstractInteger extends IntTinyType {

        public AbstractInteger(int value) {
            super(value);
        }
    }

    public static class NoOneArgCtorInteger extends IntTinyType {

        public NoOneArgCtorInteger() {
            super((int) 1);
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

    public static class LongIndirectAncestor extends Samples.Long {

        public LongIndirectAncestor(long value) {
            super(value);
        }
    }

    public static abstract class AbstractLong extends LongTinyType {

        public AbstractLong(long value) {
            super(value);
        }
    }

    public static class NoOneArgCtorLong extends LongTinyType {

        public NoOneArgCtorLong() {
            super((long) 1);
        }
    }

}
