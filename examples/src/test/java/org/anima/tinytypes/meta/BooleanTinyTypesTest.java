package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class BooleanTinyTypesTest {

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

    public static class BooleanUndirectAncestor extends Samples.Boolean {

        public BooleanUndirectAncestor(boolean value) {
            super(value);
        }
    }

}
