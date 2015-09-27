package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class ShortTinyTypesTest {

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsShortTT() {
        final boolean got = new ShortTinyTypes().isMetaOf(Samples.Short.class);
        Assert.assertTrue(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsNotShortTT() {
        final boolean got = new ShortTinyTypes().isMetaOf(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenAncestorOfCandidateIsShortTTButNotDirectSuperclass() {
        final boolean got = new ShortTinyTypes().isMetaOf(ShortUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsShortTT() {
        final boolean got = ShortTinyTypes.includes(Samples.Short.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotShortTT() {
        final boolean got = ShortTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsShortTTButNotDirectSuperclass() {
        final boolean got = ShortTinyTypes.includes(ShortUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void stringifyYieldsStringificationOfValue() {
        final String expected = "1";
        final String got = new ShortTinyTypes().stringify(new Samples.Short((short) 1));
        Assert.assertEquals(expected, got);
    }

    public static class ShortUndirectAncestor extends Samples.Short {

        public ShortUndirectAncestor(short value) {
            super(value);
        }
    }

}
