package tech.anima.tinytypes.meta;

import tech.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ByteTinyTypesTest {

    public static class IsMetaOf {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsByteTT() {
            final boolean got = new ByteTinyTypes().isMetaOf(Samples.Byte.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotByteTT() {
            final boolean got = new ByteTinyTypes().isMetaOf(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsByteTTButNotDirectSuperclass() {
            final boolean got = new ByteTinyTypes().isMetaOf(Samples.ByteIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseForNull() {
            final boolean got = new ByteTinyTypes().isMetaOf(null);
            Assert.assertFalse(got);
        }

    }

    public static class Includes {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsByteTT() {
            final boolean got = ByteTinyTypes.includes(Samples.Byte.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotByteTT() {
            final boolean got = ByteTinyTypes.includes(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsByteTTButNotDirectSuperclass() {
            final boolean got = ByteTinyTypes.includes(Samples.ByteIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenNull() {
            final boolean got = ByteTinyTypes.includes(null);
            Assert.assertFalse(got);
        }

    }

    public static class NewInstance {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTWrappingGivenValue() {
            final Samples.Byte expected = new Samples.Byte((byte) 1);
            final Samples.Byte got = new ByteTinyTypes().newInstance(Samples.Byte.class, (byte) 1);
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new ByteTinyTypes().newInstance(Samples.ByteIndirectAncestor.class, (byte) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonByteCastableValue() {
            new ByteTinyTypes().newInstance(Samples.Byte.class, (short) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForAbstractTT() {
            new ByteTinyTypes().newInstance(Samples.AbstractByte.class, (byte) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForTTWithNoOneargCtor() {
            new ByteTinyTypes().newInstance(Samples.NoOneArgCtorByte.class, (byte) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new ByteTinyTypes().newInstance(null, (byte) 1);
        }

    }

    public static class FromString {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTParsingValue() {
            final Samples.Byte expected = new Samples.Byte((byte) 1);
            final Samples.Byte got = new ByteTinyTypes().fromString(Samples.Byte.class, "1");
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonByteParseableValue() {
            new ByteTinyTypes().fromString(Samples.Byte.class, "a");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullValue() {
            new ByteTinyTypes().fromString(Samples.Byte.class, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullClass() {
            new ByteTinyTypes().fromString(null, "1");
        }

    }

    public static class Stringify {

        @Test
        public void yieldsStringificationOfValue() {
            final String expected = "1";
            final String got = new ByteTinyTypes().stringify(new Samples.Byte((byte) 1));
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new ByteTinyTypes().stringify(null);
        }

    }

}
