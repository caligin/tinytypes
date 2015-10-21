package org.anima.tinytypes.meta;

import org.anima.tinytypes.Samples;
import org.anima.tinytypes.Samples.ShortIndirectAncestor;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShortTinyTypesTest {

    public static class IsMetaOf {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsShortTT() {
            final boolean got = new ShortTinyTypes().isMetaOf(Samples.Short.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotShortTT() {
            final boolean got = new ShortTinyTypes().isMetaOf(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsShortTTButNotDirectSuperclass() {
            final boolean got = new ShortTinyTypes().isMetaOf(ShortIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseForNull() {
            final boolean got = new ShortTinyTypes().isMetaOf(null);
            Assert.assertFalse(got);
        }

    }

    public static class Includes {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsShortTT() {
            final boolean got = ShortTinyTypes.includes(Samples.Short.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotShortTT() {
            final boolean got = ShortTinyTypes.includes(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsShortTTButNotDirectSuperclass() {
            final boolean got = ShortTinyTypes.includes(ShortIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenNull() {
            final boolean got = ShortTinyTypes.includes(null);
            Assert.assertFalse(got);
        }

    }

    public static class NewInstance {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTWrappingGivenValue() {
            final Samples.Short expected = new Samples.Short((short) 1);
            final Samples.Short got = new ShortTinyTypes().newInstance(Samples.Short.class, (short) 1);
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new ShortTinyTypes().newInstance(ShortIndirectAncestor.class, (short) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonShortCastableValue() {
            new ShortTinyTypes().newInstance(Samples.Short.class, (byte) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForAbstractTT() {
            new ShortTinyTypes().newInstance(Samples.AbstractShort.class, (short) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForTTWithNoOneargCtor() {
            new ShortTinyTypes().newInstance(Samples.NoOneArgCtorShort.class, (short) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new ShortTinyTypes().newInstance(null, (short) 1);
        }

    }

    public static class FromString {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTParsingValue() {
            final Samples.Short expected = new Samples.Short((short) 1);
            final Samples.Short got = new ShortTinyTypes().fromString(Samples.Short.class, "1");
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonShortParseableValue() {
            new ShortTinyTypes().fromString(Samples.Short.class, "a");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullValue() {
            new ShortTinyTypes().fromString(Samples.Short.class, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullClass() {
            new ShortTinyTypes().fromString(null, "1");
        }

    }

    public static class Stringify {

        @Test
        public void yieldsStringificationOfValue() {
            final String expected = "1";
            final String got = new ShortTinyTypes().stringify(new Samples.Short((short) 1));
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new ShortTinyTypes().stringify(null);
        }
    }

}
