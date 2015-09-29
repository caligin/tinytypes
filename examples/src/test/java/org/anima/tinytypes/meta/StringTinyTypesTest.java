package org.anima.tinytypes.meta;

import org.anima.tinytypes.Samples;
import org.junit.Test;
import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class StringTinyTypesTest {

    public static class IsMetaOf {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsStringTT() {
            final boolean got = new StringTinyTypes().isMetaOf(Samples.Str.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsNotStringTT() {
            final boolean got = new StringTinyTypes().isMetaOf(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsTrueWhenAncestorOfCandidateIsStringTTButNotDirectSuperclass() {
            final boolean got = new StringTinyTypes().isMetaOf(Samples.StrIndirectAncestor.class);
            Assert.assertFalse(got);
        }

    }

    public static class Includes {

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsStringTT() {
            final boolean got = StringTinyTypes.includes(Samples.Str.class);
            Assert.assertTrue(got);
        }

        @Test
        public void yieldsTrueWhenCandidateSuperclassIsNotStringTT() {
            final boolean got = StringTinyTypes.includes(Samples.class);
            Assert.assertFalse(got);
        }

        @Test
        public void yieldsTrueWhenAncestorOfCandidateIsStringTTButNotDirectSuperclass() {
            final boolean got = StringTinyTypes.includes(Samples.StrIndirectAncestor.class);
            Assert.assertFalse(got);
        }
    }

    public static class NewInstance {

        @Test
        public void yieldsNewInstanceOfSpecifiedTTWrappingGivenValue() {
            final Samples.Str expected = new Samples.Str("1");
            final Samples.Str got = new StringTinyTypes().newInstance(Samples.Str.class, "1");
            Assert.assertEquals(expected, got);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonTT() {
            new StringTinyTypes().newInstance(Samples.StrIndirectAncestor.class, "1");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForNonStrCastableValue() {
            new StringTinyTypes().newInstance(Samples.Str.class, (byte) 1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForAbstractTT() {
            new StringTinyTypes().newInstance(Samples.AbstractStr.class, "1");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwsForTTWithNoOneargCtor() {
            new StringTinyTypes().newInstance(Samples.NoOneArgCtorStr.class, "1");
        }

    }

    public static class Stringify {

        @Test
        public void yieldsIdentityOfValue() {
            final String expected = "asd";
            final String got = new StringTinyTypes().stringify(new Samples.Str("asd"));
            Assert.assertEquals(expected, got);
        }
    }

}
