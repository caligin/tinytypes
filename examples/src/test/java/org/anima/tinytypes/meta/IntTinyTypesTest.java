package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class IntTinyTypesTest {

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

    public static class IntUndirectAncestor extends Samples.Integer {

        public IntUndirectAncestor(int value) {
            super(value);
        }
    }

}
