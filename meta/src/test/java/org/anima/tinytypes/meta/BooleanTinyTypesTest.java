package org.anima.tinytypes.meta;

import org.anima.tinytypes.Samples;
import org.anima.tinytypes.Samples.BooleanIndirectAncestor;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BooleanTinyTypesTest {

    public static class IsMetaOf {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsBooleanTT() {
            final boolean got = new BooleanTinyTypes().isMetaOf(Samples.Boolean.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotBooleanTT() {
            final boolean got = new BooleanTinyTypes().isMetaOf(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsBooleanTTButNotDirectSuperclass() {
            final boolean got = new BooleanTinyTypes().isMetaOf(BooleanIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseForNull() {
            final boolean got = new BooleanTinyTypes().isMetaOf(null);
            Assert.assertFalse(got);
        }

    }

    public static class Includes {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsBooleanTT() {
            final boolean got = BooleanTinyTypes.includes(Samples.Boolean.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsFalseWhenCandidateSuperclassIsNotBooleanTT() {
            final boolean got = BooleanTinyTypes.includes(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenAncestorOfCandidateIsBooleanTTButNotDirectSuperclass() {
            final boolean got = BooleanTinyTypes.includes(BooleanIndirectAncestor.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsFalseWhenNull() {
            final boolean got = BooleanTinyTypes.includes(null);
            Assert.assertFalse(got);
        }
    }

    public static class NewInstance {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTWrappingGivenValue() {
            final Samples.Boolean expected = new Samples.Boolean((boolean) true);
            final Samples.Boolean got = new BooleanTinyTypes().newInstance(Samples.Boolean.class, true);
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new BooleanTinyTypes().newInstance(Samples.BooleanIndirectAncestor.class, true);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonBooleanCastableValue() {
            new BooleanTinyTypes().newInstance(Samples.Boolean.class, 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForAbstractTT() {
            new BooleanTinyTypes().newInstance(Samples.AbstractBoolean.class, true);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForTTWithNoOneargCtor() {
            new BooleanTinyTypes().newInstance(Samples.NoOneArgCtorBoolean.class, true);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullValue() {
            new BooleanTinyTypes().newInstance(null, true);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullClass() {
            new BooleanTinyTypes().newInstance(null, false);
        }

    }

    public static class FromString {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTParsingValue() {
            final Samples.Boolean expected = new Samples.Boolean((boolean) true);
            final Samples.Boolean got = new BooleanTinyTypes().fromString(Samples.Boolean.class, "true");
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullValue() {
            new BooleanTinyTypes().fromString(Samples.Boolean.class, null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNullClass() {
            new BooleanTinyTypes().fromString(null, "false");
        }

    }

    public static class Stringify {

        @Test
        public void yieldsStringificationOfValue() {
            final String expected = "true";
            final String got = new BooleanTinyTypes().stringify(new Samples.Boolean(true));
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNull() {
            new BooleanTinyTypes().stringify(null);
        }
    }

}
