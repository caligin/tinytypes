package tech.anima.tinytypes.meta;

import tech.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class IntTinyTypesTest {

    public static class IsMetaOf {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsIntTT() {
            final boolean got = new IntTinyTypes().isMetaOf(Samples.Integer.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotIntTT() {
            final boolean got = new IntTinyTypes().isMetaOf(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsIntTTButNotDirectSuperclass() {
            final boolean got = new IntTinyTypes().isMetaOf(Samples.IntIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseForNull() {
            final boolean got = new IntTinyTypes().isMetaOf(null);
            Assert.assertFalse(got);
        }
    }

    public static class Includes {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsIntTT() {
            final boolean got = IntTinyTypes.includes(Samples.Integer.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotIntTT() {
            final boolean got = IntTinyTypes.includes(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsIntTTButNotDirectSuperclass() {
            final boolean got = IntTinyTypes.includes(Samples.IntIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenNull() {
            final boolean got = IntTinyTypes.includes(null);
            Assert.assertFalse(got);
        }

    }

    public static class NewInstance {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTWrappingGivenValue() {
            final Samples.Integer expected = new Samples.Integer(1);
            final Samples.Integer got = new IntTinyTypes().newInstance(Samples.Integer.class, 1);
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new IntTinyTypes().newInstance(Samples.IntIndirectAncestor.class, 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonIntCastableValue() {
            new IntTinyTypes().newInstance(Samples.Integer.class, true);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForAbstractTT() {
            new IntTinyTypes().newInstance(Samples.AbstractInteger.class, 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForTTWithNoOneargCtor() {
            new IntTinyTypes().newInstance(Samples.NoOneArgCtorInteger.class, 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new IntTinyTypes().newInstance(null, 1);
        }

    }

    public static class FromString {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTParsingValue() {
            final Samples.Integer expected = new Samples.Integer((int) 1);
            final Samples.Integer got = new IntTinyTypes().fromString(Samples.Integer.class, "1");
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonIntParseableValue() {
            new IntTinyTypes().fromString(Samples.Integer.class, "a");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullValue() {
            new IntTinyTypes().fromString(Samples.Integer.class, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullClass() {
            new IntTinyTypes().fromString(null, "1");
        }

    }

    public static class Stringify {

        @Test
        public void yieldsStringificationOfValue() {
            final String expected = "1";
            final String got = new IntTinyTypes().stringify(new Samples.Integer(1));
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new IntTinyTypes().stringify(null);
        }

    }

}
