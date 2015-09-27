package org.anima.tinytypes.meta;

import org.anima.tinytypes.examples.Samples;
import org.junit.Test;
import org.junit.Assert;

public class ShortTinyTypesTest {

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

    public static class ShortUndirectAncestor extends Samples.Short {

        public ShortUndirectAncestor(short value) {
            super(value);
        }
    }

}
