package tech.anima.tinytypes.meta;

import tech.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class LongTinyTypesTest {

    public static class IsMetaOf {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsLongTT() {
            final boolean got = new LongTinyTypes().isMetaOf(Samples.Long.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotLongTT() {
            final boolean got = new LongTinyTypes().isMetaOf(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsLongTTButNotDirectSuperclass() {
            final boolean got = new LongTinyTypes().isMetaOf(Samples.LongIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseForNull() {
            final boolean got = new LongTinyTypes().isMetaOf(null);
            Assert.assertFalse(got);
        }

    }

    public static class Includes {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsLongTT() {
            final boolean got = LongTinyTypes.includes(Samples.Long.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotLongTT() {
            final boolean got = LongTinyTypes.includes(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsLongTTButNotDirectSuperclass() {
            final boolean got = LongTinyTypes.includes(Samples.LongIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenNull() {
            final boolean got = LongTinyTypes.includes(null);
            Assert.assertFalse(got);
        }

    }

    public static class NewInstance {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTWrappingGivenValue() {
            final Samples.Long expected = new Samples.Long((long) 1);
            final Samples.Long got = new LongTinyTypes().newInstance(Samples.Long.class, (long) 1);
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new LongTinyTypes().newInstance(Samples.LongIndirectAncestor.class, (long) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonLongCastableValue() {
            new LongTinyTypes().newInstance(Samples.Long.class, true);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForAbstractTT() {
            new LongTinyTypes().newInstance(Samples.AbstractLong.class, (long) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForTTWithNoOneargCtor() {
            new LongTinyTypes().newInstance(Samples.NoOneArgCtorLong.class, (long) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new LongTinyTypes().newInstance(null, 1);
        }

    }

    public static class FromString {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTParsingValue() {
            final Samples.Long expected = new Samples.Long((long) 1);
            final Samples.Long got = new LongTinyTypes().fromString(Samples.Long.class, "1");
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonLongParseableValue() {
            new LongTinyTypes().fromString(Samples.Long.class, "a");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullValue() {
            new LongTinyTypes().fromString(Samples.Long.class, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullClass() {
            new LongTinyTypes().fromString(null, "1");
        }

    }

    public static class Stringify {

        @Test
        public void yieldsStringificationOfValue() {
            final String expected = "1";
            final String got = new LongTinyTypes().stringify(new Samples.Long(1));
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new LongTinyTypes().stringify(null);
        }

    }

}
