package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class ByteTinyTypesTest {

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsByteTT() {
        final boolean got = new ByteTinyTypes().isMetaOf(Samples.Byte.class);
        Assert.assertTrue(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenCandidateSuperclassIsNotByteTT() {
        final boolean got = new ByteTinyTypes().isMetaOf(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void isMetaOfYieldsTrueWhenAncestorOfCandidateIsByteTTButNotDirectSuperclass() {
        final boolean got = new ByteTinyTypes().isMetaOf(ByteUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsByteTT() {
        final boolean got = ByteTinyTypes.includes(Samples.Byte.class);
        Assert.assertTrue(got);
    }

    @Test
    public void includesYieldsTrueWhenCandidateSuperclassIsNotByteTT() {
        final boolean got = ByteTinyTypes.includes(Samples.class);
        Assert.assertFalse(got);
    }

    @Test
    public void includesYieldsTrueWhenAncestorOfCandidateIsByteTTButNotDirectSuperclass() {
        final boolean got = ByteTinyTypes.includes(ByteUndirectAncestor.class);
        Assert.assertFalse(got);
    }

    @Test
    public void stringifyYieldsStringificationOfValue() {
        final String expected = "1";
        final String got = new ByteTinyTypes().stringify(new Samples.Byte((byte) 1));
        Assert.assertEquals(expected, got);
    }

    public static class ByteUndirectAncestor extends Samples.Byte {

        public ByteUndirectAncestor(byte value) {
            super(value);
        }
    }

}
