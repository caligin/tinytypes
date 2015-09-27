package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class BooleanTinyTypesTest {

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsBooleanTT() {
        final boolean got = new BooleanTinyTypes().isMetaOf(Samples.Boolean.class);
        Assert.assertTrue(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsNotBooleanTT() {
        final boolean got = new BooleanTinyTypes().isMetaOf(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenAncestorOfCandidateIsBooleanTTButNotDirectSuperclass() {
        final boolean got = new BooleanTinyTypes().isMetaOf(BooleanUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsBooleanTT() {
        final boolean got = BooleanTinyTypes.includes(Samples.Boolean.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotBooleanTT() {
        final boolean got = BooleanTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsBooleanTTButNotDirectSuperclass() {
        final boolean got = BooleanTinyTypes.includes(BooleanUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void stringifyYieldsStringificationOfValue() {
        final String expected = "true";
        final String got = new BooleanTinyTypes().stringify(new Samples.Boolean(true));
        Assert.assertEquals(expected, got);
    }

    public static class BooleanUndirectAncestor extends Samples.Boolean {

        public BooleanUndirectAncestor(boolean value) {
            super(value);
        }
    }

}