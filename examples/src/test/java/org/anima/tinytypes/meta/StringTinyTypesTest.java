package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class StringTinyTypesTest {

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsStringTT() {
        final boolean got = new StringTinyTypes().isMetaOf(Samples.Str.class);
        Assert.assertTrue(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsNotStringTT() {
        final boolean got = new StringTinyTypes().isMetaOf(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenAncestorOfCandidateIsStringTTButNotDirectSuperclass() {
        final boolean got = new StringTinyTypes().isMetaOf(StringUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsStringTT() {
        final boolean got = StringTinyTypes.includes(Samples.Str.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotStringTT() {
        final boolean got = StringTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsStringTTButNotDirectSuperclass() {
        final boolean got = StringTinyTypes.includes(StringUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void stringifyYieldsIdentityOfValue() {
        final String expected = "asd";
        final String got = new StringTinyTypes().stringify(new Samples.Str("asd"));
        Assert.assertEquals(expected, got);
    }

    public static class StringUndirectAncestor extends Samples.Str {

        public StringUndirectAncestor(String value) {
            super(value);
        }
    }

}
