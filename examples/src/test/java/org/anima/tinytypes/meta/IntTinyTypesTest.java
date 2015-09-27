package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class IntTinyTypesTest {

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsIntTT() {
        final boolean got = new IntTinyTypes().isMetaOf(Samples.Integer.class);
        Assert.assertTrue(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsNotIntTT() {
        final boolean got = new IntTinyTypes().isMetaOf(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenAncestorOfCandidateIsIntTTButNotDirectSuperclass() {
        final boolean got = new IntTinyTypes().isMetaOf(IntUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsIntTT() {
        final boolean got = IntTinyTypes.includes(Samples.Integer.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotIntTT() {
        final boolean got = IntTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsIntTTButNotDirectSuperclass() {
        final boolean got = IntTinyTypes.includes(IntUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void stringifyYieldsStringificationOfValue() {
        final String expected = "1";
        final String got = new IntTinyTypes().stringify(new Samples.Integer(1));
        Assert.assertEquals(expected, got);
    }

    public static class IntUndirectAncestor extends Samples.Integer {

        public IntUndirectAncestor(int value) {
            super(value);
        }
    }

}
