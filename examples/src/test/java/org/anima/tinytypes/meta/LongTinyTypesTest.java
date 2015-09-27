package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class LongTinyTypesTest {

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsLongTT() {
        final boolean got = new LongTinyTypes().isMetaOf(Samples.Long.class);
        Assert.assertTrue(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsNotLongTT() {
        final boolean got = new LongTinyTypes().isMetaOf(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenAncestorOfCandidateIsLongTTButNotDirectSuperclass() {
        final boolean got = new LongTinyTypes().isMetaOf(LongUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsLongTT() {
        final boolean got = LongTinyTypes.includes(Samples.Long.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotLongTT() {
        final boolean got = LongTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsLongTTButNotDirectSuperclass() {
        final boolean got = LongTinyTypes.includes(LongUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void stringifyYieldsStringificationOfValue() {
        final String expected = "1";
        final String got = new LongTinyTypes().stringify(new Samples.Long(1));
        Assert.assertEquals(expected, got);
    }

    public static class LongUndirectAncestor extends Samples.Long {

        public LongUndirectAncestor(long value) {
            super(value);
        }
    }

}